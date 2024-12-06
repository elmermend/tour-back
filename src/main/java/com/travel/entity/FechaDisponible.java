package com.travel.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class FechaDisponible {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fecha;

    private int stock;

    private int duracionDias;
    private int disponibilidad = 0;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    public FechaDisponible(){}
    public FechaDisponible(Long id, LocalDate fecha, int stock, Producto producto) {
        this.id = id;
        this.fecha = fecha;
        this.stock = stock;
        this.producto = producto;
    }
    public boolean tieneStock() {
        return stock > disponibilidad;
    }

    public void incrementarDisponibilidad() {
        if (tieneStock()) {
            disponibilidad++;
        } else {
            throw new IllegalStateException("No hay stock disponible");
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getDuracionDias() {
        return duracionDias;
    }

    public void setDuracionDias(int duracionDias) {
        this.duracionDias = duracionDias;
    }

    public int getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(int disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}
