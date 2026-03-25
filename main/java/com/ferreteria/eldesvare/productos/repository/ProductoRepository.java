package com.ferreteria.eldesvare.productos.repository;

import com.ferreteria.eldesvare.productos.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductoRepository extends JpaRepository<Producto, Long> {

    List<Producto> findTop3ByOrderByIdDesc();

    List<Producto> findByCategoriaId(Long categoriaId);


}