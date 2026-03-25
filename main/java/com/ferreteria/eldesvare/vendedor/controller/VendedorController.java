package com.ferreteria.eldesvare.vendedor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VendedorController {

    @GetMapping("/vendedor/dashboard")
    public String dashboard(){

        return "vendedor/dashboard";

    }

}