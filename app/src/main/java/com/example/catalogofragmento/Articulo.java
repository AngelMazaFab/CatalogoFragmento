package com.example.catalogofragmento;

public class Articulo {

    private String nombre;
    private String descripcion;

    private double precio;
    private int imagen;

    public Articulo(String nombre, String descripcion, double precio, int imagen) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion(){
        return descripcion;
    }

    public double getPrecio(){
        return precio;
    }

    public void setNombre(String nombre){
        this.nombre = nombre;
    }

    public void setDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public void setPrecio(double precio){
        this.precio = precio;
    }

    public int getImagen(){
        return imagen;
    }
}