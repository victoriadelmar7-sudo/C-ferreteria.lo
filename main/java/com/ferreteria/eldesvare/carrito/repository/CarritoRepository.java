package com.ferreteria.eldesvare.carrito.repository;

import com.ferreteria.eldesvare.carrito.model.Carrito;
import com.ferreteria.eldesvare.productos.model.Producto;
import com.ferreteria.eldesvare.clientes.model.Cliente;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {

    // BUSCAR PRODUCTOS DEL CARRITO DEL CLIENTE
    List<Carrito> findByCliente(Cliente cliente);

    // BUSCAR SI EL PRODUCTO YA ESTÁ EN EL CARRITO
    Carrito findByProductoAndCliente(Producto producto, Cliente cliente);

}