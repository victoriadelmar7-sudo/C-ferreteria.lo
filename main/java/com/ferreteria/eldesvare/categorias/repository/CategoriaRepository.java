package com.ferreteria.eldesvare.categorias.repository;

import com.ferreteria.eldesvare.categorias.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}