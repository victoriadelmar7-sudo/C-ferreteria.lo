package com.ferreteria.eldesvare.admin.controller;

import com.ferreteria.eldesvare.pedidos.model.Pedido;
import com.ferreteria.eldesvare.pedidos.repository.PedidoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class AdminController {

    private final PedidoRepository pedidoRepository;

    public AdminController(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    // 🔹 PANEL ADMIN
    @GetMapping("/admin/dashboard")
    public String dashboard(){
        return "admin/dashboard";
    }


}