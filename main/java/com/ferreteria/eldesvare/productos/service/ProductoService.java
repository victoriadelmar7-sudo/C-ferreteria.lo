package com.ferreteria.eldesvare.productos.service;

import com.ferreteria.eldesvare.productos.model.Producto;
import com.ferreteria.eldesvare.productos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    // 🔹 LISTAR
    public List<Producto> listar(){
        return productoRepository.findAll();
    }

    // 🔹 GUARDAR
    public void guardar(Producto producto){
        productoRepository.save(producto);
    }

    // 🔹 ELIMINAR
    public void eliminar(Long id){
        productoRepository.deleteById(id);
    }

    // 🔹 OBTENER POR ID (IMPORTANTE PARA EDITAR)
    public Producto obtenerPorId(Long id){
        return productoRepository.findById(id).orElse(null);
    }
}