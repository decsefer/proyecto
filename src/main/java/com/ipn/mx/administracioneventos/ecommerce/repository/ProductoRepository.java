package com.ipn.mx.administracioneventos.ecommerce.repository;

import com.ipn.mx.administracioneventos.ecommerce.domain.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
    List<Producto> findByCategoria_IdCategoria(Long idCategoria);
    List<Producto> findByNombreContainingIgnoreCase(String q);
    Optional<Producto> findByNombre(String nombre);
}
