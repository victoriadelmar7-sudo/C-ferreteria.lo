package com.ferreteria.eldesvare.productos.model;

import com.ferreteria.eldesvare.proveedores.model.Proveedor;
import com.ferreteria.eldesvare.categorias.model.Categoria;

import jakarta.persistence.*;

@Entity
@Table(name = "productos") // 🔥 SOLUCIÓN CLAVE
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private double precio;
    private int stock;

    @ManyToOne
    @JoinColumn(name = "proveedor_id") // recomendable
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "categoria_id") // recomendable
    private Categoria categoria;

    public Producto(){}

    // GETTERS Y SETTERS

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
}