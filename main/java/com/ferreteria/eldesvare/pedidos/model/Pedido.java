package com.ferreteria.eldesvare.pedidos.model;

import com.ferreteria.eldesvare.clientes.model.Cliente;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime fecha;

    private double total;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL)
    private List<PedidoDetalle> detalles;

    public Pedido() {
        this.fecha = LocalDateTime.now();
        this.detalles = new ArrayList<>(); // 🔥 SOLUCIÓN CLAVE
    }

    // 🔥 MÉTODO IMPORTANTE
    public void agregarDetalle(PedidoDetalle detalle) {
        detalle.setPedido(this); // conecta el detalle con el pedido
        this.detalles.add(detalle);
    }

    // GETTERS Y SETTERS
    public Long getId() { return id; }
    public LocalDateTime getFecha() { return fecha; }
    public double getTotal() { return total; }
    public Cliente getCliente() { return cliente; }
    public List<PedidoDetalle> getDetalles() { return detalles; }

    public void setTotal(double total) { this.total = total; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
}