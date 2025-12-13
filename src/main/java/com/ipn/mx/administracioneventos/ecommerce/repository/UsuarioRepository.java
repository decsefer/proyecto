package com.ipn.mx.administracioneventos.ecommerce.repository;

import com.ipn.mx.administracioneventos.ecommerce.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}

