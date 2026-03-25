package com.ferreteria.eldesvare.carrito.model;

import com.ferreteria.eldesvare.productos.model.Producto;
import com.ferreteria.eldesvare.clientes.model.Cliente;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    private int cantidad;

    public Carrito() {}

    public Carrito(Producto producto, Cliente cliente, int cantidad) {
        this.producto = producto;
        this.cliente = cliente;
        this.cantidad = cantidad;
    }

    public Long getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}