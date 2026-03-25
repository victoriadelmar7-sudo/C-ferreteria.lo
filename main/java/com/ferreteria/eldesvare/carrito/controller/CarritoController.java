package com.ferreteria.eldesvare.carrito.controller;

import com.ferreteria.eldesvare.carrito.model.Carrito;
import com.ferreteria.eldesvare.carrito.service.CarritoService;
import com.ferreteria.eldesvare.clientes.model.Cliente;
import com.ferreteria.eldesvare.clientes.service.ClienteService;
import com.ferreteria.eldesvare.pedidos.model.Pedido;
import com.ferreteria.eldesvare.pedidos.model.PedidoDetalle;
import com.ferreteria.eldesvare.pedidos.repository.PedidoDetalleRepository;
import com.ferreteria.eldesvare.pedidos.repository.PedidoRepository;
import com.ferreteria.eldesvare.productos.model.Producto;
import com.ferreteria.eldesvare.productos.service.ProductoService;
import com.ferreteria.eldesvare.usuarios.model.Usuario;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/carrito")
public class CarritoController {

    private final CarritoService carritoService;
    private final ProductoService productoService;
    private final ClienteService clienteService;
    private final PedidoRepository pedidoRepository;


    public CarritoController(CarritoService carritoService,
                             ProductoService productoService,
                             ClienteService clienteService,
                             PedidoRepository pedidoRepository,
                             PedidoDetalleRepository pedidoDetalleRepository) {
        this.carritoService = carritoService;
        this.productoService = productoService;
        this.clienteService = clienteService;
        this.pedidoRepository = pedidoRepository;
    }

    // 🔹 CONTADOR DEL CARRITO
    @ModelAttribute("cantidadCarrito")
    public int cantidadCarrito(HttpSession session){
        Integer cantidad = (Integer) session.getAttribute("cantidadCarrito");
        return (cantidad != null) ? cantidad : 0;
    }

    // 🔹 MÉTODO AUXILIAR PARA OBTENER O CREAR CLIENTE
    private Cliente obtenerOCrearCliente(Usuario usuario){
        Cliente cliente = clienteService.buscarPorEmail(usuario.getCorreo());

        if(cliente == null){
            cliente = new Cliente();
            cliente.setNombre(usuario.getNombre());
            cliente.setEmail(usuario.getCorreo());
            clienteService.obtenerOCrearCliente(cliente);
        }
        return cliente;
    }

    // 🔹 VER CARRITO
    @GetMapping
    public String verCarrito(Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/usuarios/login";
        }

        Cliente cliente = obtenerOCrearCliente(usuario);
        List<Carrito> carrito = carritoService.obtenerPorCliente(cliente);

        double total = 0;
        for(Carrito item : carrito){
            total += item.getProducto().getPrecio() * item.getCantidad();
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);
        session.setAttribute("cantidadCarrito", carrito.size());

        return "carrito/carrito";
    }

    // 🔹 AGREGAR PRODUCTO
    @PostMapping("/agregar")
    public String agregarProducto(@RequestParam Long productoId,
                                  HttpSession session,
                                  RedirectAttributes redirectAttributes){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/usuarios/login";
        }

        Producto producto = productoService.obtenerPorId(productoId);
        if(producto == null){
            redirectAttributes.addFlashAttribute("mensaje","Producto no encontrado");
            return "redirect:/productos";
        }

        Cliente cliente = obtenerOCrearCliente(usuario);
        Carrito itemExistente = carritoService.buscarProductoCliente(producto, cliente);

        if(itemExistente != null){
            itemExistente.setCantidad(itemExistente.getCantidad() + 1);
            carritoService.guardar(itemExistente);
        } else {
            Carrito item = new Carrito(producto, cliente, 1);
            carritoService.guardar(item);
        }

        List<Carrito> carrito = carritoService.obtenerPorCliente(cliente);
        session.setAttribute("cantidadCarrito", carrito.size());
        redirectAttributes.addFlashAttribute("mensaje","Producto agregado al carrito correctamente");

        return "redirect:/productos";
    }

    // 🔹 SUMAR PRODUCTO
    @PostMapping("/sumar/{id}")
    public String sumar(@PathVariable Long id){
        carritoService.sumarCantidad(id);
        return "redirect:/carrito";
    }

    // 🔹 RESTAR PRODUCTO
    @PostMapping("/restar/{id}")
    public String restar(@PathVariable Long id){
        carritoService.restarCantidad(id);
        return "redirect:/carrito";
    }

    // 🔹 ELIMINAR PRODUCTO
    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id){
        carritoService.eliminar(id);
        return "redirect:/carrito";
    }

    // 🔹 RESUMEN CARRITO
    @GetMapping("/resumen")
    public String resumen(Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/usuarios/login";
        }

        Cliente cliente = obtenerOCrearCliente(usuario);
        List<Carrito> carrito = carritoService.obtenerPorCliente(cliente);

        double total = 0;
        for(Carrito item : carrito){
            total += item.getProducto().getPrecio() * item.getCantidad();
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "carrito/resumen";
    }

    // 🔹 CHECKOUT
    @GetMapping("/checkout")
    public String checkout(Model model, HttpSession session){
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/usuarios/login";
        }

        Cliente cliente = obtenerOCrearCliente(usuario);
        List<Carrito> carrito = carritoService.obtenerPorCliente(cliente);

        double total = 0;
        for(Carrito item : carrito){
            total += item.getProducto().getPrecio() * item.getCantidad();
        }

        model.addAttribute("carrito", carrito);
        model.addAttribute("total", total);

        return "carrito/checkout";
    }

    // 🔹 FINALIZAR COMPRA (PLAN B INFALIBLE)
    @PostMapping("/finalizar")
    public String finalizarCompra(HttpSession session){

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null){
            return "redirect:/usuarios/login";
        }

        Cliente cliente = obtenerOCrearCliente(usuario);
        List<Carrito> carrito = carritoService.obtenerPorCliente(cliente);

        if(carrito.isEmpty()){
            return "redirect:/carrito";
        }

        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);

        double total = 0;

        for(Carrito item : carrito){

            PedidoDetalle detalle = new PedidoDetalle();
            detalle.setProducto(item.getProducto());
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio(item.getProducto().getPrecio());

            // 🔥 ESTA ES LA CLAVE
            pedido.agregarDetalle(detalle);

            total += item.getProducto().getPrecio() * item.getCantidad();
        }

        pedido.setTotal(total);

        // 🔥 GUARDA TODO JUNTO (gracias a cascade)
        pedidoRepository.save(pedido);

        // limpiar carrito
        carritoService.vaciarCarrito(cliente);

        return "redirect:/pedidos";
    }
}