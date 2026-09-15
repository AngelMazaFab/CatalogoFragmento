package com.example.catalogofragmento;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentLista#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentLista extends Fragment {

    private ListView listaArticulos;
    private android.widget.TextView tvContadorLista;

    private ArrayList<Articulo> articulos;
    private ArrayList<Articulo> articulosFiltrados;

    private ArticuloAdapter adapter;

    private OnArticuloSeleccionadoListener listener;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public FragmentLista() {

    }
    public interface OnArticuloSeleccionadoListener{
        void onArticuloSeleccionado(Articulo articulo);
    }

    @Override
    public void onAttach(@NonNull Context context){
        super.onAttach(context);

        if(context instanceof OnArticuloSeleccionadoListener){
            listener = (OnArticuloSeleccionadoListener) context;
        }
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentLista.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentLista newInstance(String param1, String param2) {
        FragmentLista fragment = new FragmentLista();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listaArticulos = view.findViewById(R.id.listaArticulos);
        tvContadorLista = view.findViewById(R.id.tvContadorLista);
        crearArticulos();
        mostrarArticulos();
    }

    private void crearArticulos(){
        articulos = new ArrayList<>();

        articulos.add(new Articulo(
                "Dell Laptop",
                "Laptop 15.6 pulgadas 8GB RAM, SSD 512GB, procesador Intel Core i5",
                13999,
                R.drawable.laptop,
                "Dell",
                8,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                " HP Laptop B0CP3LA",
                "Laptop 15.6 1366x768 HD, AMD Ryzen 7 7730U, 16GB, 512GB SSD, Windows 11 Home",
                14700,
                R.drawable.laptop02,
                "HP",
                7,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                "Coolby Laptop Windows",
                "Laptop Windows de 15.6 pulgadas, 8 GB de RAM DDR4 256 GB M.2 SSD, computadoras portátiles Intel J4115Quad-Core, 1080P IPS Windows10 Pro PC",
                18000,
                R.drawable.laptop03,
                "Coolby",
                13,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                " Logitech Mouse inalámbrico",
                "Mouse ergonómico con conexión Bluetooth 5.0 y receptor USB",
                299,
                R.drawable.mouse,
                "Logitech",
                8,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "Multi Mouse alámbrico",
                "Mouse alámbrico | Multi | USB, 1200dpi",
                179,
                R.drawable.mouse02,
                "Multi",
                29,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "Xiaomi Mouse Inalámbrico",
                "Mouse Inalámbrico Xiaomi Wireless Mouse Lite 2 White",
                169,
                R.drawable.mouse03,
                "Xioami",
                19,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "HyperX Teclado Mecánico",
                "Teclado mecánico RGB con switches Cherry MX, retroiluminado",
                1500,
                R.drawable.teclado,
                "HyperX",
                8,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "Genérico Teclado de juegos",
                "Teclado de juegos membrana con 61 teclas retroiluminación RGB cool cable cableado separado diseño simple y compacto",
                382,
                R.drawable.teclado02,
                "Genérica",
                500,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "Logitech Media Teclado",
                "Logitech Media Teclado Elite- Negro (967559 – 0403)",
                2601,
                R.drawable.teclado03,
                "Logitech",
                10,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "Samsung Monitor",
                "Monitor LED 32 pulgadas Full HD 4K, 60Hz, panel IPS",
                5000,
                R.drawable.monitor,
                "Samsung",
                8,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                "Samsung Monitor Curvo",
                "Monitor Curvo Samsung Essential S3 LCD 27, 1920x1080 Full HD, 60Hz, HDMI, Negro",
                3028,
                R.drawable.monitor02,
                "Samsung",
                5,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                "KUAI Monitor LED",
                "Monitor LED OEM 24 27 32 Pulgadas Monitores de Juego para PC IPS 75Hz 144Hz 165Hz 1ms Pantalla de Computadora LED de Alta Definición 24 Pulgadas",
                3028,
                R.drawable.monitor03,
                "KUAI",
                14,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                "JBL Audifonos Bluetooth",
                "Audífonos inalámbricos con cancelación de ruido activa",
                1299,
                R.drawable.audifonos,
                "JBL",
                8,
                "Audio"
        ));

        articulos.add(new Articulo(
                "Sony Audífonos inalámbricos WH-CH520",
                "Adapta el sonido a tus preferencias personales con la app Sony",
                689,
                R.drawable.audifonos02,
                "Sony",
                20,
                "Audio"
        ));

        articulos.add(new Articulo(
                "Gabba Goods Audifonos Platinum Vibe",
                "Botones de control de volumen",
                450,
                R.drawable.audifonos03,
                "Gabba Goods",
                10,
                "Audio"
        ));

        articulos.add(new Articulo(
                "Kingston Memoria USB",
                "Memoria USB 3.0 de 128GB, velocidad de lectura 150MB/s",
                500,
                R.drawable.usb,
                "Kingston",
                8,
                "Almacenamiento"
        ));

        articulos.add(new Articulo(
                "Adata Memoria USB",
                "Memoria USB Adata 64GB Metalica 3.2 UV350",
                299,
                R.drawable.usb02,
                "Adata",
                30,
                "Almacenamiento"
        ));

        articulos.add(new Articulo(
                "Sandisk Pen Drive",
                "Pen Drive Sandisk Crayola USB-C de 64 GB con memoria de datos portátil, azul",
                691,
                R.drawable.usb03,
                "Sandisk",
                1,
                "Almacenamiento"
        ));



        articulosFiltrados = new ArrayList<>(articulos);
    }

    private void mostrarArticulos(){
        adapter = new ArticuloAdapter(requireContext(), articulosFiltrados);
        listaArticulos.setAdapter(adapter);
        actualizarContador();

        listaArticulos.setOnItemClickListener((parent, view, position, id) -> {
            Articulo articuloSeleccionado = adapter.getItem(position);

            if(listener!=null){
                listener.onArticuloSeleccionado(articuloSeleccionado);
            }
        });
    }

    /**
     * Filtra la lista de artículos por texto de búsqueda y categoría.
     * Invocado desde MainActivity cuando cambia el buscador o el spinner.
     */
    public void filtrar(String texto, String categoria) {
        articulosFiltrados.clear();

        for (Articulo articulo : articulos) {
            boolean coincideTexto = texto == null || texto.isEmpty()
                    || articulo.getNombre().toLowerCase().contains(texto.toLowerCase());
            boolean coincideCategoria;
            if (categoria == null || categoria.isEmpty() || categoria.equalsIgnoreCase("Todos")) {
                coincideCategoria = true;
            } else if (categoria.equalsIgnoreCase("Favoritos")) {
                coincideCategoria = articulo.isFavorito();
            } else {
                coincideCategoria = articulo.getCategoria().equalsIgnoreCase(categoria);
            }

            if (coincideTexto && coincideCategoria) {
                articulosFiltrados.add(articulo);
            }
        }

        adapter.actualizarLista(articulosFiltrados);
        actualizarContador();
    }

    private void actualizarContador() {
        if (tvContadorLista != null) {
            int count = articulosFiltrados.size();
            tvContadorLista.setText(count + " Disponible" + (count != 1 ? "s" : ""));
        }
    }
}