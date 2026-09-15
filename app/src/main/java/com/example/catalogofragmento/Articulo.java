package com.example.catalogofragmento;

public class Articulo implements java.io.Serializable {

    private String nombre;
    private String descripcion;
    private double precio;
    private int imagen;
    private String marca;
    private int existencia;      // unidades en stock
    private String categoria;    // "Computadoras", "Accesorios", "Audio", "Almacenamiento"
    private boolean favorito;    // estado del botón ⭐, inicia en false


    public Articulo(String nombre, String descripcion, double precio, int imagen,
                    String marca, int existencia, String categoria) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.imagen = imagen;
        this.marca = marca;
        this.existencia = existencia;
        this.categoria = categoria;
        this.favorito = false;
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

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public boolean isDisponible() {
        return existencia > 0;
    }
}