package com.ferreteria.eldesvare.pedidos.repository;

import com.ferreteria.eldesvare.pedidos.model.PedidoDetalle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PedidoDetalleRepository extends JpaRepository<PedidoDetalle, Long> {
}