package com.travel.entity;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

// Clase que representa un producto
@Entity
public class Producto {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private long id; // ID del producto

    private String nombre; // Nombre del producto
    private String descripcion; // Descripción del producto
    private String region; // Región donde se ofrece el producto
    private int cantidad; // Cantidad disponible
    private double precio; // Precio del producto
    private List<String> imagenes; // Lista para múltiples imágenes del producto
    private Date fecha; // Fecha de disponibilidad del producto
    private int categoria; // Categoría si es en grupo, solo o mujeres

    public Producto() {}

    // Constructor que inicializa los atributos
    public Producto(long id, String nombre, String descripcion, String region, int cantidad, double precio, List<String> imagenes, Date fecha, int categoria) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.region = region;
        this.cantidad = cantidad;
        this.precio = precio;
        this.imagenes = imagenes;
        this.fecha = fecha;
        this.categoria = categoria;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public List<String> getImagenes() {
        return imagenes;
    }

    public void setImagenes(List<String> imagenes) {
        this.imagenes = imagenes;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }
    
}