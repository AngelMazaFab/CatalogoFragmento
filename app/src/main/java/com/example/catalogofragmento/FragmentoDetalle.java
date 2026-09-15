package com.example.catalogofragmento;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentoDetalle#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentoDetalle extends Fragment {

    private TextView txtNombre, txtDescripcion, txtPrecio;
    private ImageView imgArticulo;
    private ImageButton btnFavorito, btnCompartir;
    private TextView tvDisponibilidad, tvCategoria, tvMarca, tvPrecioTotal, tvCantidad;
    private Button btnMenos, btnMas, btnComprar;

    private Articulo articuloActual;
    private int cantidadSeleccionada = 1;

    private OnArticuloCompradoListener listener;
    private OnFavoritoCambiadoListener favoritoListener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentoDetalle() {
        // Required empty public constructor
    }

    /**
     * Interfaz de callback para comunicar la compra a MainActivity.
     */
    public interface OnArticuloCompradoListener {
        void onArticuloComprado(Articulo articulo, int cantidad);
    }

    /**
     * Interfaz de callback para comunicar el cambio de favorito a MainActivity.
     */
    public interface OnFavoritoCambiadoListener {
        void onFavoritoCambiado(Articulo articulo);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnArticuloCompradoListener) {
            listener = (OnArticuloCompradoListener) context;
        }
        if (context instanceof OnFavoritoCambiadoListener) {
            favoritoListener = (OnFavoritoCambiadoListener) context;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentoDetalle.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentoDetalle newInstance(String param1, String param2) {
        FragmentoDetalle fragment = new FragmentoDetalle();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detalle, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Controles existentes
        txtNombre = view.findViewById(R.id.txtNombre);
        txtDescripcion = view.findViewById(R.id.txtDescripcion);
        txtPrecio = view.findViewById(R.id.txtPrecio);
        imgArticulo = view.findViewById(R.id.imgArticulo);

        // Controles nuevos
        btnFavorito = view.findViewById(R.id.btnFavorito);
        btnCompartir = view.findViewById(R.id.btnCompartir);
        tvDisponibilidad = view.findViewById(R.id.tvDisponibilidad);
        tvCategoria = view.findViewById(R.id.tvCategoria);
        tvMarca = view.findViewById(R.id.tvMarca);
        tvPrecioTotal = view.findViewById(R.id.tvPrecioTotal);
        tvCantidad = view.findViewById(R.id.tvCantidad);
        btnMenos = view.findViewById(R.id.btnMenos);
        btnMas = view.findViewById(R.id.btnMas);
        btnComprar = view.findViewById(R.id.btnAgregarCarrito);

        // Favorito toggle
        btnFavorito.setOnClickListener(v -> {
            if (articuloActual != null) {
                articuloActual.setFavorito(!articuloActual.isFavorito());
                actualizarIconoFavorito();
                actualizarColorPrecioFavorito();
                if (favoritoListener != null) {
                    favoritoListener.onFavoritoCambiado(articuloActual);
                }
                String msg = articuloActual.isFavorito() ? "Añadido a favoritos" : "Eliminado de favoritos";
                Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(requireContext(), "Selecciona un artículo para marcarlo como favorito", Toast.LENGTH_SHORT).show();
            }
        });

        // Compartir
        btnCompartir.setOnClickListener(v -> {
            if (articuloActual != null) {
                compartirArticulo();
            } else {
                Toast.makeText(requireContext(), "Selecciona un artículo para compartirlo", Toast.LENGTH_SHORT).show();
            }
        });

        // Selector de cantidad
        btnMenos.setOnClickListener(v -> {
            if (articuloActual == null) {
                Toast.makeText(requireContext(), "Selecciona un artículo primero", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cantidadSeleccionada > 1) {
                cantidadSeleccionada--;
                actualizarCantidadYTotal();
            } else {
                Toast.makeText(requireContext(), "La cantidad mínima es 1", Toast.LENGTH_SHORT).show();
            }
        });

        btnMas.setOnClickListener(v -> {
            if (articuloActual == null) {
                Toast.makeText(requireContext(), "Selecciona un artículo primero", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!articuloActual.isDisponible() || articuloActual.getExistencia() <= 0) {
                Toast.makeText(requireContext(), "El artículo está agotado", Toast.LENGTH_SHORT).show();
                return;
            }
            if (cantidadSeleccionada < articuloActual.getExistencia()) {
                cantidadSeleccionada++;
                actualizarCantidadYTotal();
            } else {
                Toast.makeText(requireContext(), "No puedes seleccionar más de las " + articuloActual.getExistencia() + " unidades disponibles", Toast.LENGTH_SHORT).show();
            }
        });

        // Botón Comprar
        btnComprar.setOnClickListener(v -> {
            if (articuloActual == null) {
                Toast.makeText(requireContext(), "Selecciona un artículo antes de comprar", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!articuloActual.isDisponible() || articuloActual.getExistencia() <= 0) {
                Toast.makeText(requireContext(), "No se puede comprar: el artículo está agotado", Toast.LENGTH_SHORT).show();
                return;
            }

            int cantidadComprada = cantidadSeleccionada;

            // Descontar existencia
            articuloActual.setExistencia(articuloActual.getExistencia() - cantidadComprada);

            // Notificar a MainActivity vía interfaz
            if (listener != null) {
                listener.onArticuloComprado(articuloActual, cantidadComprada);
            }

            Toast.makeText(requireContext(), "Agregado al carrito (" + cantidadComprada + " unidad" + (cantidadComprada > 1 ? "es" : "") + ")", Toast.LENGTH_SHORT).show();

            // Resetear cantidad y actualizar vista
            cantidadSeleccionada = 1;
            actualizarCantidadYTotal();
            actualizarDisponibilidad();

            // Si se agotó, actualizar aspecto del botón
            if (!articuloActual.isDisponible()) {
                btnComprar.setText("Agotado");
                btnComprar.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.rojo_agotado));
            }
        });
    }

    public void mostrarArticulo(Articulo articulo) {
        if (articulo != null) {
            this.articuloActual = articulo;
            this.cantidadSeleccionada = 1;

            txtNombre.setText(articulo.getNombre());
            txtDescripcion.setText(articulo.getDescripcion());
            txtPrecio.setText(String.format("Precio: $%,.2f", articulo.getPrecio()));
            imgArticulo.setImageResource(articulo.getImagen());

            tvCategoria.setText("Categoría: " + articulo.getCategoria());
            tvMarca.setText("Marca: " + articulo.getMarca());

            actualizarIconoFavorito();
            actualizarColorPrecioFavorito();
            actualizarDisponibilidad();
            actualizarCantidadYTotal();

            // Actualizar apariencia del botón según disponibilidad
            if (articulo.isDisponible()) {
                btnComprar.setText("Comprar");
                btnComprar.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.azul_ml));
            } else {
                btnComprar.setText("Agotado");
                btnComprar.setBackgroundTintList(ContextCompat.getColorStateList(requireContext(), R.color.rojo_agotado));
            }
        }
    }

    private void actualizarColorPrecioFavorito() {
        if (articuloActual != null && txtPrecio != null && isAdded()) {
            int color;
            if (articuloActual.isFavorito()) {
                color = ContextCompat.getColor(requireContext(), R.color.amarillo_favorito);
            } else {
                color = ContextCompat.getColor(requireContext(), R.color.azul_ml);
            }
            txtPrecio.setTextColor(color);
            if (tvPrecioTotal != null) {
                tvPrecioTotal.setTextColor(color);
            }
        }
    }

    private void actualizarIconoFavorito() {
        if (articuloActual != null) {
            if (articuloActual.isFavorito()) {
                btnFavorito.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                btnFavorito.setImageResource(android.R.drawable.btn_star_big_off);
            }
        }
    }

    private void actualizarDisponibilidad() {
        if (articuloActual != null) {
            if (articuloActual.isDisponible()) {
                tvDisponibilidad.setText("Disponible (" + articuloActual.getExistencia() + ")");
                tvDisponibilidad.setBackgroundColor(
                        getResources().getColor(R.color.verde_disponible, requireContext().getTheme()));
            } else {
                tvDisponibilidad.setText("Agotado");
                tvDisponibilidad.setBackgroundColor(
                        getResources().getColor(R.color.rojo_agotado, requireContext().getTheme()));
            }
        }
    }

    private void actualizarCantidadYTotal() {
        if (articuloActual != null) {
            // Ajustar si la cantidad excede la existencia actual
            if (cantidadSeleccionada > articuloActual.getExistencia()) {
                cantidadSeleccionada = Math.max(1, articuloActual.getExistencia());
            }
            tvCantidad.setText(String.valueOf(cantidadSeleccionada));
            double total = articuloActual.getPrecio() * cantidadSeleccionada;
            tvPrecioTotal.setText(String.format("Total: $%,.2f", total));
        }
    }

    private void compartirArticulo() {
        Intent intent = new Intent(requireContext(), CompartirActivity.class);
        intent.putExtra("nombre", articuloActual.getNombre());
        intent.putExtra("descripcion", articuloActual.getDescripcion());
        intent.putExtra("precio", articuloActual.getPrecio());
        intent.putExtra("imagen", articuloActual.getImagen());
        intent.putExtra("marca", articuloActual.getMarca());
        intent.putExtra("categoria", articuloActual.getCategoria());
        intent.putExtra("favorito", articuloActual.isFavorito());
        startActivity(intent);
    }
}