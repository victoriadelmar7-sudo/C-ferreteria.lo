package com.ferreteria.eldesvare.facturas.controller;

import com.ferreteria.eldesvare.facturas.model.Factura;
import com.ferreteria.eldesvare.facturas.service.FacturaService;
import com.ferreteria.eldesvare.pedidos.repository.PedidoRepository; // 👈 IMPORTANTE
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final PedidoRepository pedidoRepository; // 👈 AQUÍ

    // 👇 CONSTRUCTOR CORRECTO
    public FacturaController(FacturaService facturaService, PedidoRepository pedidoRepository) {
        this.facturaService = facturaService;
        this.pedidoRepository = pedidoRepository;
    }

    // LISTAR (USANDO PEDIDOS)
    @GetMapping("/listar")
    public String listarFacturas(Model model) {

        model.addAttribute("facturas", pedidoRepository.findAll()); // 👈 YA FUNCIONA

        return "facturas/detallefactura/listar";
    }

    @PostMapping("/crear")
    public String crearFactura() {
        return "redirect:/facturas/compra-exitosa";
    }

    @PostMapping("/guardar")
    public String guardarFactura(@ModelAttribute Factura factura) {
        facturaService.guardarFactura(factura);
        return "redirect:/facturas/listar";
    }

    @GetMapping("/compra-exitosa")
    public String compraExitosa() {
        return "compra-exitosa";
    }
}