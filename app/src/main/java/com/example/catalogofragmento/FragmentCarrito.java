package com.example.catalogofragmento;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Fragment que muestra el carrito de compras.
 * Expone el método público agregarItem() para que MainActivity le pase artículos.
 */
public class FragmentCarrito extends Fragment implements CarritoAdapter.OnCarritoCambiadoListener {

    private ListView lvCarrito;
    private TextView tvTotalCarrito;
    private TextView tvBadgeCarrito;
    private Button btnComprarCarrito;

    private ArrayList<ItemCarrito> itemsCarrito;
    private CarritoAdapter adapter;

    public FragmentCarrito() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_carrito, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lvCarrito = view.findViewById(R.id.lvCarrito);
        tvTotalCarrito = view.findViewById(R.id.tvTotalCarrito);
        tvBadgeCarrito = view.findViewById(R.id.tvBadgeCarrito);
        btnComprarCarrito = view.findViewById(R.id.btnComprarCarrito);

        itemsCarrito = new ArrayList<>();
        adapter = new CarritoAdapter(requireContext(), itemsCarrito, this);
        lvCarrito.setAdapter(adapter);

        btnComprarCarrito.setOnClickListener(v -> {
            if (itemsCarrito == null || itemsCarrito.isEmpty()) {
                Toast.makeText(requireContext(), "No puedes comprar: el carrito está vacío", Toast.LENGTH_SHORT).show();
            } else {
                itemsCarrito.clear();
                adapter.notifyDataSetChanged();
                actualizarTotal();
                Toast.makeText(requireContext(), "Compra Exitosa", Toast.LENGTH_SHORT).show();
            }
        });

        actualizarTotal();
    }

    /**
     * Agrega un artículo al carrito. Si ya existe, suma la cantidad.
     * Invocado desde MainActivity cuando FragmentoDetalle notifica una compra.
     */
    public void agregarItem(Articulo articulo, int cantidad) {
        // Buscar si el artículo ya está en el carrito
        for (ItemCarrito item : itemsCarrito) {
            if (item.getArticulo() == articulo) {
                item.setCantidad(item.getCantidad() + cantidad);
                adapter.notifyDataSetChanged();
                actualizarTotal();
                return;
            }
        }

        // Si no existe, agregar nuevo
        itemsCarrito.add(new ItemCarrito(articulo, cantidad));
        adapter.notifyDataSetChanged();
        actualizarTotal();
    }

    @Override
    public void onCarritoCambiado() {
        actualizarTotal();
    }

    private void actualizarTotal() {
        double total = 0;
        int totalItems = 0;
        for (ItemCarrito item : itemsCarrito) {
            total += item.getSubtotal();
            totalItems += item.getCantidad();
        }
        tvTotalCarrito.setText(String.format("$%,.2f", total));
        if (tvBadgeCarrito != null) {
            tvBadgeCarrito.setText(totalItems + " Item" + (totalItems != 1 ? "s" : ""));
        }
    }
}
