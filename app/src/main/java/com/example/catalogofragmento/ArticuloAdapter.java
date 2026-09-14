package com.example.catalogofragmento;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Articulo articulo = articulos.get(position);
        holder.imgArticulo.setImageResource(articulo.getImagen());
        holder.tvNombre.setText(articulo.getNombre());
        holder.tvPrecio.setText(String.format("$%,.2f", articulo.getPrecio()));

        return convertView;
    }

    private static class ViewHolder {
        ImageView imgArticulo;
        TextView tvNombre;
        TextView tvPrecio;
    }
}
