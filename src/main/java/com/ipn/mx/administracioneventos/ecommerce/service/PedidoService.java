package com.ipn.mx.administracioneventos.ecommerce.service;

import com.ipn.mx.administracioneventos.ecommerce.domain.Pedido;

import java.math.BigDecimal;
import java.util.List;

public interface PedidoService {
    Pedido generarPedido(Long idUsuario);
    List<Pedido> listar();
    Pedido obtener(Long idPedido);
    List<Pedido> listarPorUsuario(Long idUsuario);
    BigDecimal total(Long idPedido);
    byte[] factura(Long idPedido);
}

