package com.example.catalogofragmento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CarritoAdapter extends BaseAdapter {

    public interface OnCarritoCambiadoListener {
        void onCarritoCambiado();
    }

    private Context context;
    private ArrayList<ItemCarrito> items;
    private LayoutInflater inflater;
    private OnCarritoCambiadoListener listener;

    public CarritoAdapter(Context context, ArrayList<ItemCarrito> items, OnCarritoCambiadoListener listener) {
        this.context = context;
        this.items = items;
        this.inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public ItemCarrito getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_carrito, parent, false);
            holder = new ViewHolder();
            holder.imgArticulo = convertView.findViewById(R.id.imgCarritoItem);
            holder.tvNombre = convertView.findViewById(R.id.tvCarritoNombre);
            holder.tvPrecioUnitario = convertView.findViewById(R.id.tvCarritoPrecioUnitario);
            holder.btnMenos = convertView.findViewById(R.id.btnCarritoMenos);
            holder.tvCantidad = convertView.findViewById(R.id.tvCarritoCantidad);
            holder.btnMas = convertView.findViewById(R.id.btnCarritoMas);
            holder.tvSubtotal = convertView.findViewById(R.id.tvCarritoSubtotal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemCarrito item = items.get(position);
        Articulo articulo = item.getArticulo();

        holder.imgArticulo.setImageResource(articulo.getImagen());
        holder.tvNombre.setText(articulo.getNombre());
        holder.tvPrecioUnitario.setText(String.format("$%,.2f c/u", articulo.getPrecio()));
        holder.tvCantidad.setText(String.valueOf(item.getCantidad()));
        holder.tvSubtotal.setText(String.format("$%,.2f", item.getSubtotal()));

        holder.btnMenos.setOnClickListener(v -> {
            int cantidadActual = item.getCantidad();
            if (cantidadActual <= 1) {
                // Devolver toda la cantidad a existencia y quitar del carrito
                articulo.setExistencia(articulo.getExistencia() + cantidadActual);
                items.remove(position);
                Toast.makeText(context, articulo.getNombre() + " eliminado del carrito", Toast.LENGTH_SHORT).show();
            } else {
                item.setCantidad(cantidadActual - 1);
                articulo.setExistencia(articulo.getExistencia() + 1);
            }
            notifyDataSetChanged();
            if (listener != null) {
                listener.onCarritoCambiado();
            }
        });

        holder.btnMas.setOnClickListener(v -> {
            if (articulo.getExistencia() > 0) {
                item.setCantidad(item.getCantidad() + 1);
                articulo.setExistencia(articulo.getExistencia() - 1);
                notifyDataSetChanged();
                if (listener != null) {
                    listener.onCarritoCambiado();
                }
            } else {
                Toast.makeText(context, "No hay más unidades en stock de " + articulo.getNombre(), Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgArticulo;
        TextView tvNombre;
        TextView tvPrecioUnitario;
        Button btnMenos;
        TextView tvCantidad;
        Button btnMas;
        TextView tvSubtotal;
    }
}
