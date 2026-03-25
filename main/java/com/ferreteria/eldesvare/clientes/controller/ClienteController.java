package com.ferreteria.eldesvare.clientes.controller;

import com.ferreteria.eldesvare.clientes.model.Cliente;
import com.ferreteria.eldesvare.clientes.service.ClienteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @GetMapping
    public List<Cliente> listar(){
        return clienteService.listarClientes();
    }

    @PostMapping
    public Cliente guardar(@RequestBody Cliente cliente){
        clienteService.guardar(cliente);
        return cliente;
    }

    @DeleteMapping("/{id}")
    public void eliminar(@PathVariable Long id){
        clienteService.eliminar(id);
    }
}