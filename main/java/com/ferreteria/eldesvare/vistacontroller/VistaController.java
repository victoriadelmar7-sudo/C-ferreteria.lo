package com.ferreteria.eldesvare.vistacontroller;

import com.ferreteria.eldesvare.productos.service.ProductoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    private final ProductoService productoService;

    public VistaController(ProductoService productoService) {
        this.productoService = productoService;
    }

    @GetMapping({"/", "/ferreteria-el-desvare"})
    public String inicio(Model model){

        model.addAttribute("destacados", productoService.listar());

        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "usuarios/login";
    }


}