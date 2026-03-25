package com.ferreteria.eldesvare.pedidos.repository;

import com.ferreteria.eldesvare.pedidos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

    @Query("SELECT p FROM Pedido p " +
            "LEFT JOIN FETCH p.detalles d " +
            "LEFT JOIN FETCH d.producto " +
            "WHERE p.cliente.id = :clienteId " +
            "ORDER BY p.fecha DESC")
    List<Pedido> obtenerPedidosConDetalles(Long clienteId);

}