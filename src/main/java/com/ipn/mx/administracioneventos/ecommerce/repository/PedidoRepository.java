package com.ipn.mx.administracioneventos.ecommerce.repository;

import com.ipn.mx.administracioneventos.ecommerce.domain.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    List<Pedido> findByUsuario_IdUsuario(Long idUsuario);
}

