package com.ferreteria.eldesvare.proveedores.controller;

import com.ferreteria.eldesvare.proveedores.model.Proveedor;
import com.ferreteria.eldesvare.proveedores.service.ProveedorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping("/proveedores")
    public String listar(Model model) {
        model.addAttribute("proveedores", proveedorService.listarProveedores());
        model.addAttribute("proveedor", new Proveedor());
        return "proveedores/proveedores";
    }

    @PostMapping("/proveedores/guardar")
    public String guardar(@ModelAttribute Proveedor proveedor) {
        proveedorService.guardar(proveedor);
        return "redirect:/proveedores";
    }

    @GetMapping("/proveedores/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        proveedorService.eliminar(id);
        return "redirect:/proveedores";
    }
}