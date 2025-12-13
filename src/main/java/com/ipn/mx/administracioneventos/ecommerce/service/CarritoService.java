package com.ipn.mx.administracioneventos.ecommerce.service;

import com.ipn.mx.administracioneventos.ecommerce.domain.Carrito;

import java.math.BigDecimal;

public interface CarritoService {
    Carrito obtenerOCrear(Long idUsuario);
    Carrito agregarProducto(Long idUsuario, Long idProducto, Integer cantidad);
    Carrito eliminarProducto(Long idUsuario, Long idProducto);
    Carrito vaciar(Long idUsuario);
    BigDecimal total(Long idUsuario);
}

