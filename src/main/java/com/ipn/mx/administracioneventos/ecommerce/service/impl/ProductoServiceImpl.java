package com.ipn.mx.administracioneventos.ecommerce.service.impl;

import com.ipn.mx.administracioneventos.ecommerce.domain.Producto;
import com.ipn.mx.administracioneventos.ecommerce.repository.ProductoRepository;
import com.ipn.mx.administracioneventos.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoServiceImpl implements ProductoService {
    @Autowired
    private ProductoRepository repo;

    @Override
    public List<Producto> listar(Long idCategoria, String q, BigDecimal minPrecio, BigDecimal maxPrecio) {
        List<Producto> base = (idCategoria != null) ? repo.findByCategoria_IdCategoria(idCategoria) : repo.findAll();
        if (q != null && !q.isBlank()) {
            base = base.stream().filter(p -> p.getNombre().toLowerCase().contains(q.toLowerCase())).collect(Collectors.toList());
        }
        if (minPrecio != null) base = base.stream().filter(p -> p.getPrecio().compareTo(minPrecio) >= 0).collect(Collectors.toList());
        if (maxPrecio != null) base = base.stream().filter(p -> p.getPrecio().compareTo(maxPrecio) <= 0).collect(Collectors.toList());
        return base;
    }

    @Override
    public Producto obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public Producto crear(Producto p) {
        return repo.save(p);
    }

    @Override
    public Producto actualizar(Long id, Producto p) {
        Producto x = repo.findById(id).orElse(null);
        if (x == null) return null;
        x.setNombre(p.getNombre());
        x.setPrecio(p.getPrecio());
        x.setStock(p.getStock());
        x.setCategoria(p.getCategoria());
        return repo.save(x);
    }

    @Override
    public Producto actualizarStock(Long id, Integer stock) {
        Producto x = repo.findById(id).orElse(null);
        if (x == null) return null;
        x.setStock(stock);
        return repo.save(x);
    }

    @Override
    public void eliminar(Long id) {
        repo.deleteById(id);
    }
}

