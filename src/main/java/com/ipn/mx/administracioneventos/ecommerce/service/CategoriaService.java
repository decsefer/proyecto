package com.ipn.mx.administracioneventos.ecommerce.service;

import com.ipn.mx.administracioneventos.ecommerce.domain.Categoria;

import java.util.List;

public interface CategoriaService {
    List<Categoria> listar();
    Categoria obtener(Long id);
    Categoria crear(Categoria c);
    Categoria actualizar(Long id, Categoria c);
    void eliminar(Long id);
}

