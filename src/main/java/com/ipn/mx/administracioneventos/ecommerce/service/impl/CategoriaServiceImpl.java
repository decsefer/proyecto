package com.ipn.mx.administracioneventos.ecommerce.service.impl;

import com.ipn.mx.administracioneventos.ecommerce.domain.Categoria;
import com.ipn.mx.administracioneventos.ecommerce.repository.CategoriaRepository;
import com.ipn.mx.administracioneventos.ecommerce.service.CategoriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaServiceImpl implements CategoriaService {
    @Autowired
    private CategoriaRepository repo;

    @Override
    public List<Categoria> listar() {
        return repo.findAll();
    }

    @Override
    public Categoria obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Categoria crear(Categoria c) {
        return repo.save(c);
    }

    @Override
    public Categoria actualizar(Long id, Categoria c) {
        Categoria x = repo.findById(id).orElse(null);
        if (x == null) return null;
        x.setNombre(c.getNombre());
        return repo.save(x);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}

