package com.ferreteria.eldesvare.categorias.controller;

import com.ferreteria.eldesvare.categorias.repository.CategoriaRepository;
import com.ferreteria.eldesvare.productos.repository.ProductoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CategoriaController {

    private final CategoriaRepository categoriaRepository;
    private final ProductoRepository productoRepository;

    public CategoriaController(CategoriaRepository categoriaRepository,
                               ProductoRepository productoRepository) {
        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
    }

    @GetMapping("/categorias")
    public String verCategorias(Model model) {

        model.addAttribute("categorias", categoriaRepository.findAll());

        return "categorias";
    }

    @GetMapping("/categorias/{id}")
    public String verProductosPorCategoria(@PathVariable Long id, Model model) {

        var categoria = categoriaRepository.findById(id).orElse(null);

        model.addAttribute("productos",
                productoRepository.findByCategoriaId(id));

        model.addAttribute("categoria", categoria); // 👈 IMPORTANTE

        return "productos/productos";
    }
}