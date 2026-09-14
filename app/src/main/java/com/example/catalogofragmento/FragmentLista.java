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
        crearArticulos();
        mostrarArticulos();
    }

    private void crearArticulos(){
        articulos = new ArrayList<>();

        articulos.add(new Articulo(
                "Laptop",
                "Laptop 15.6 pulgadas 8GB RAM, SSD 512GB, procesador Intel Core i5",
                13999,
                R.drawable.laptop,
                "Dell",
                8,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                "Mouse inalámbrico",
                "Mouse ergonómico con conexión Bluetooth 5.0 y receptor USB",
                299,
                R.drawable.mouse,
                "Logitech",
                15,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "Teclado Mecánico",
                "Teclado mecánico RGB con switches Cherry MX, retroiluminado",
                1500,
                R.drawable.teclado,
                "HyperX",
                10,
                "Accesorios"
        ));

        articulos.add(new Articulo(
                "Monitor",
                "Monitor LED 32 pulgadas Full HD 4K, 60Hz, panel IPS",
                5000,
                R.drawable.monitor,
                "Samsung",
                5,
                "Computadoras"
        ));

        articulos.add(new Articulo(
                "Audifonos Bluetooth",
                "Audífonos inalámbricos con cancelación de ruido activa",
                1299,
                R.drawable.audifonos,
                "JBL",
                3,
                "Audio"
        ));

        articulos.add(new Articulo(
                "Memoria USB",
                "Memoria USB 3.0 de 128GB, velocidad de lectura 150MB/s",
                500,
                R.drawable.usb,
                "Kingston",
                20,
                "Almacenamiento"
        ));

        articulosFiltrados = new ArrayList<>(articulos);
    }

    private void mostrarArticulos(){
        adapter = new ArticuloAdapter(requireContext(), articulosFiltrados);
        listaArticulos.setAdapter(adapter);

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
            boolean coincideCategoria = categoria == null || categoria.isEmpty()
                    || categoria.equals("Todos")
                    || articulo.getCategoria().equals(categoria);

            if (coincideTexto && coincideCategoria) {
                articulosFiltrados.add(articulo);
            }
        }

        adapter.actualizarLista(articulosFiltrados);
    }
}