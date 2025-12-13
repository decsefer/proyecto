package com.ipn.mx.administracioneventos.ecommerce.repository;

import com.ipn.mx.administracioneventos.ecommerce.domain.Carrito;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CarritoRepository extends JpaRepository<Carrito, Long> {
    Optional<Carrito> findByUsuario_IdUsuarioAndActivoTrue(Long idUsuario);
}

