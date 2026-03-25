package com.ferreteria.eldesvare.pedidos.controller;

import com.ferreteria.eldesvare.clientes.model.Cliente;
import com.ferreteria.eldesvare.clientes.service.ClienteService;
import com.ferreteria.eldesvare.pedidos.model.Pedido;
import com.ferreteria.eldesvare.pedidos.repository.PedidoRepository;
import com.ferreteria.eldesvare.usuarios.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;

    public PedidoController(PedidoRepository pedidoRepository, ClienteService clienteService) {
        this.pedidoRepository = pedidoRepository;
        this.clienteService = clienteService;
    }

    @GetMapping
    public String historialPedidos(Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/usuarios/login";
        }

        Cliente cliente = clienteService.buscarPorEmail(usuario.getCorreo());

        // ✅ Lista de pedidos del cliente
        List<Pedido> pedidos = pedidoRepository.obtenerPedidosConDetalles(cliente.getId());

        model.addAttribute("pedidos", pedidos);
        return "pedidos/listar";
    }
}