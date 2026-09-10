package com.example.catalogofragmento;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
                "Laptop 15.6 pulgadas 8GB RAM",
                13999,
                R.drawable.laptop
        ));

        articulos.add(new Articulo(
                "Mouse inalámbrico",
                "Mouse con conexion Bluetooth",
                299,
                R.drawable.mouse
        ));

        articulos.add(new Articulo(
                "Teclado Mecánico",
                "Es como un teclado, pero mecánico",
                1500,
                R.drawable.teclado
        ));

        articulos.add(new Articulo(
                "Monitor",
                "Monitor LED 32 pulgadas Full HD 4k",
                5000,
                R.drawable.monitor
        ));

        articulos.add(new Articulo(
                "Audifonos Bluetooth",
                "Audifonos inalámbricos",
                1299,
                R.drawable.audifonos
        ));

        articulos.add(new Articulo(
                "Memoria USB",
                "Memoria de 128GB",
                500,
                R.drawable.usb
        ));


    }

    private void mostrarArticulos(){
        ArrayList<String> nombres = new ArrayList<>();

        for(Articulo articulo : articulos){
            nombres.add(articulo.getNombre());
        }

        ArrayAdapter <String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, nombres);

        listaArticulos.setAdapter(adapter);

        listaArticulos.setOnItemClickListener((parent, view, position, id) -> {
            Articulo articuloSeleccionado = articulos.get(position);
            MainActivity activity = (MainActivity) requireActivity();
            activity.mostrarDetalle(articuloSeleccionado);

            if(listener!=null){
                listener.onArticuloSeleccionado(articuloSeleccionado);
            }
        });
    }
}