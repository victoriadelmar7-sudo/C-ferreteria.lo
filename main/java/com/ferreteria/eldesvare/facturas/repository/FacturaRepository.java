package com.ferreteria.eldesvare.facturas.repository;

import com.ferreteria.eldesvare.facturas.model.Factura;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacturaRepository extends JpaRepository<Factura, Long> {
}