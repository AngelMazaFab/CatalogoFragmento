package com.example.catalogofragmento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class ArticuloAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Articulo> articulos;
    private LayoutInflater inflater;

    public ArticuloAdapter(Context context, ArrayList<Articulo> articulos) {
        this.context = context;
        this.articulos = new ArrayList<>(articulos);
        this.inflater = LayoutInflater.from(context);
    }

    public void actualizarLista(ArrayList<Articulo> nuevaLista) {
        this.articulos.clear();
        this.articulos.addAll(nuevaLista);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return articulos.size();
    }

    @Override
    public Articulo getItem(int position) {
        return articulos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_articulo, parent, false);
            holder = new ViewHolder();
            holder.imgArticulo = convertView.findViewById(R.id.imgItemArticulo);
            holder.tvNombre = convertView.findViewById(R.id.tvItemNombre);
            holder.tvPrecio = convertView.findViewById(R.id.tvItemPrecio);
            holder.tvStock = convertView.findViewById(R.id.tvItemStock);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Articulo articulo = articulos.get(position);
        holder.imgArticulo.setImageResource(articulo.getImagen());
        holder.tvNombre.setText(articulo.getNombre());
        holder.tvPrecio.setText(String.format("$%,.2f", articulo.getPrecio()));

        if (articulo.isFavorito()) {
            holder.tvPrecio.setTextColor(ContextCompat.getColor(context, R.color.amarillo_favorito));
        } else {
            holder.tvPrecio.setTextColor(ContextCompat.getColor(context, R.color.azul_ml));
        }

        // Chip de stock
        if (holder.tvStock != null) {
            if (articulo.isDisponible()) {
                holder.tvStock.setText("Stock");
                holder.tvStock.setBackgroundResource(R.drawable.bg_chip_stock);
                holder.tvStock.setTextColor(ContextCompat.getColor(context, R.color.white));
            } else {
                holder.tvStock.setText("Agotado");
                holder.tvStock.setBackgroundResource(R.drawable.bg_chip_stock);
                holder.tvStock.setBackgroundTintList(
                    android.content.res.ColorStateList.valueOf(
                        ContextCompat.getColor(context, R.color.rojo_agotado)));
                holder.tvStock.setTextColor(ContextCompat.getColor(context, R.color.white));
            }
        }

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgArticulo;
        TextView tvNombre;
        TextView tvPrecio;
        TextView tvStock;
    }
}
