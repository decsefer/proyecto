package com.ipn.mx.administracioneventos.ecommerce.service;

import com.ipn.mx.administracioneventos.ecommerce.domain.Producto;

import java.math.BigDecimal;
import java.util.List;

public interface ProductoService {
    List<Producto> listar(Long idCategoria, String q, BigDecimal minPrecio, BigDecimal maxPrecio);
    Producto obtener(Long id);
    Producto crear(Producto p);
    Producto actualizar(Long id, Producto p);
    Producto actualizarStock(Long id, Integer stock);
    void eliminar(Long id);
}

