package com.example.catalogofragmento;

public class ItemCarrito {

    private Articulo articulo;
    private int cantidad;

    public ItemCarrito(Articulo articulo, int cantidad) {
        this.articulo = articulo;
        this.cantidad = cantidad;
    }

    public Articulo getArticulo() {
        return articulo;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getSubtotal() {
        return articulo.getPrecio() * cantidad;
    }
}
