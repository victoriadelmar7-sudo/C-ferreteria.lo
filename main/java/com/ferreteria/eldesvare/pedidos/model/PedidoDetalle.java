package com.ferreteria.eldesvare.pedidos.model;

import com.ferreteria.eldesvare.productos.model.Producto;
import jakarta.persistence.*;

@Entity
@Table(name = "pedido_detalle")
public class PedidoDetalle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cantidad;
    private double precio;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    public PedidoDetalle(){}

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public int getCantidad() { return cantidad; }
    public double getPrecio() { return precio; }
    public Producto getProducto() { return producto; }
    public Pedido getPedido() { return pedido; }

    public void setCantidad(int cantidad) { this.cantidad = cantidad; }
    public void setPrecio(double precio) { this.precio = precio; }
    public void setProducto(Producto producto) { this.producto = producto; }
    public void setPedido(Pedido pedido) { this.pedido = pedido; }
}