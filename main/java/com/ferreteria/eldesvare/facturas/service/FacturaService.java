package com.ferreteria.eldesvare.facturas.service;

import com.ferreteria.eldesvare.facturas.model.Factura;
import com.ferreteria.eldesvare.facturas.repository.FacturaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaService {

    private final FacturaRepository facturaRepository;

    public FacturaService(FacturaRepository facturaRepository) {
        this.facturaRepository = facturaRepository;
    }

    public List<Factura> listarFacturas() {
        return facturaRepository.findAll();
    }

    public Factura guardarFactura(Factura factura) {
        return facturaRepository.save(factura);
    }

    public Factura obtenerFactura(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }
}