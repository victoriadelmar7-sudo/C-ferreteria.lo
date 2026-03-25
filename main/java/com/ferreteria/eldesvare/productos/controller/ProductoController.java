package com.ferreteria.eldesvare.productos.controller;

import com.ferreteria.eldesvare.productos.model.Producto;
import com.ferreteria.eldesvare.productos.service.ProductoService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/productos")
public class ProductoController {

    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // 🔹 CLIENTE
    @GetMapping
    public String listar(Model model){
        model.addAttribute("productos", productoService.listar());
        return "productos/productos";
    }

    // 🔹 ADMIN
    @GetMapping("/admin")
    public String adminProductos(Model model) {
        model.addAttribute("productos", productoService.listar());
        model.addAttribute("producto", new Producto()); // vacío
        return "productos/productos-admin";
    }

    // 🔹 EDITAR (CARGA EL PRODUCTO EN EL FORMULARIO)
    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model){

        Producto producto = productoService.obtenerPorId(id);

        model.addAttribute("producto", producto); // 👈 ESTE LLENA EL FORM
        model.addAttribute("productos", productoService.listar());

        return "productos/productos-admin";
    }

    // 🔹 GUARDAR (SIRVE PARA CREAR Y EDITAR)
    @PostMapping("/guardar")
    public String guardarProducto(@ModelAttribute Producto producto){

        productoService.guardar(producto); // 👈 SI TIENE ID → EDITA

        return "redirect:/productos/admin";
    }

    // 🔹 ELIMINAR
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id){
        productoService.eliminar(id);
        return "redirect:/productos/admin";
    }
}