package com.ferreteria.eldesvare.config;

import com.ferreteria.eldesvare.productos.model.Producto;
import com.ferreteria.eldesvare.productos.repository.ProductoRepository;
import com.ferreteria.eldesvare.categorias.model.Categoria;
import com.ferreteria.eldesvare.categorias.repository.CategoriaRepository;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitData {

    @Bean
    CommandLineRunner cargarDatos(ProductoRepository productoRepository,
                                  CategoriaRepository categoriaRepository) {

        return args -> {

            // SOLO insertar productos si la tabla está vacía
            if (productoRepository.count() == 0) {

                Categoria herramientas = categoriaRepository.findById(1L).orElse(null);
                Categoria materiales = categoriaRepository.findById(2L).orElse(null);
                Categoria electricidad = categoriaRepository.findById(3L).orElse(null);

                // 🔧 HERRAMIENTAS
                Producto p1 = new Producto();
                p1.setNombre("Martillo");
                p1.setPrecio(20000.0);
                p1.setStock(15);
                p1.setCategoria(herramientas);

                Producto p2 = new Producto();
                p2.setNombre("Destornillador");
                p2.setPrecio(8000.0);
                p2.setStock(30);
                p2.setCategoria(herramientas);

                Producto p3 = new Producto();
                p3.setNombre("Taladro");
                p3.setPrecio(150000.0);
                p3.setStock(10);
                p3.setCategoria(herramientas);

                Producto p4 = new Producto();
                p4.setNombre("Alicate");
                p4.setPrecio(18000.0);
                p4.setStock(20);
                p4.setCategoria(herramientas);

                Producto p5 = new Producto();
                p5.setNombre("Llave inglesa");
                p5.setPrecio(25000.0);
                p5.setStock(12);
                p5.setCategoria(herramientas);

                // 🧱 MATERIALES
                Producto p6 = new Producto();
                p6.setNombre("Cemento");
                p6.setPrecio(28000.0);
                p6.setStock(50);
                p6.setCategoria(materiales);

                Producto p7 = new Producto();
                p7.setNombre("Arena");
                p7.setPrecio(15000.0);
                p7.setStock(100);
                p7.setCategoria(materiales);

                Producto p8 = new Producto();
                p8.setNombre("Grava");
                p8.setPrecio(20000.0);
                p8.setStock(60);
                p8.setCategoria(materiales);

                Producto p9 = new Producto();
                p9.setNombre("Ladrillos");
                p9.setPrecio(1200.0);
                p9.setStock(500);
                p9.setCategoria(materiales);

                Producto p10 = new Producto();
                p10.setNombre("Varilla de acero");
                p10.setPrecio(35000.0);
                p10.setStock(40);
                p10.setCategoria(materiales);

                // ⚡ ELECTRICIDAD
                Producto p11 = new Producto();
                p11.setNombre("Cable eléctrico");
                p11.setPrecio(12000.0);
                p11.setStock(80);
                p11.setCategoria(electricidad);

                Producto p12 = new Producto();
                p12.setNombre("Interruptor");
                p12.setPrecio(7000.0);
                p12.setStock(35);
                p12.setCategoria(electricidad);

                Producto p13 = new Producto();
                p13.setNombre("Tomacorriente");
                p13.setPrecio(8000.0);
                p13.setStock(40);
                p13.setCategoria(electricidad);

                Producto p14 = new Producto();
                p14.setNombre("Bombillo LED");
                p14.setPrecio(9000.0);
                p14.setStock(60);
                p14.setCategoria(electricidad);

                Producto p15 = new Producto();
                p15.setNombre("Breaker");
                p15.setPrecio(30000.0);
                p15.setStock(25);
                p15.setCategoria(electricidad);

                productoRepository.save(p1);
                productoRepository.save(p2);
                productoRepository.save(p3);
                productoRepository.save(p4);
                productoRepository.save(p5);
                productoRepository.save(p6);
                productoRepository.save(p7);
                productoRepository.save(p8);
                productoRepository.save(p9);
                productoRepository.save(p10);
                productoRepository.save(p11);
                productoRepository.save(p12);
                productoRepository.save(p13);
                productoRepository.save(p14);
                productoRepository.save(p15);

            }
        };
    }
}
