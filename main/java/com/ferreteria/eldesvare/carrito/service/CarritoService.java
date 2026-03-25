package com.ferreteria.eldesvare.carrito.service;

import com.ferreteria.eldesvare.carrito.model.Carrito;
import com.ferreteria.eldesvare.carrito.repository.CarritoRepository;
import com.ferreteria.eldesvare.productos.model.Producto;
import com.ferreteria.eldesvare.clientes.model.Cliente;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarritoService {

    private final CarritoRepository carritoRepository;

    public CarritoService(CarritoRepository carritoRepository) {
        this.carritoRepository = carritoRepository;
    }

    public void guardar(Carrito carrito){
        carritoRepository.save(carrito);
    }

    public List<Carrito> obtenerPorCliente(Cliente cliente){
        return carritoRepository.findByCliente(cliente);
    }

    // 🔹 MÉTODO NUEVO PARA BUSCAR PRODUCTO EN CARRITO
    public Carrito buscarProductoCliente(Producto producto, Cliente cliente){
        return carritoRepository.findByProductoAndCliente(producto, cliente);
    }

    // SUMAR CANTIDAD
    public void sumarCantidad(Long id){

        Carrito item = carritoRepository.findById(id).orElse(null);

        if(item != null){
            item.setCantidad(item.getCantidad() + 1);
            carritoRepository.save(item);
        }

    }

    // RESTAR CANTIDAD
    public void restarCantidad(Long id){

        Carrito item = carritoRepository.findById(id).orElse(null);

        if(item != null && item.getCantidad() > 1){
            item.setCantidad(item.getCantidad() - 1);
            carritoRepository.save(item);
        }

    }

    // ELIMINAR PRODUCTO DEL CARRITO
    public void eliminar(Long id){
        carritoRepository.deleteById(id);
    }


    public void vaciarCarrito(Cliente cliente){
        List<Carrito> items = carritoRepository.findByCliente(cliente);
        carritoRepository.deleteAll(items);
    }


}