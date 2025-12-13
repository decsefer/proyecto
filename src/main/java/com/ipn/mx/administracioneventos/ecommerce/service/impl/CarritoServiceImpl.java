package com.ipn.mx.administracioneventos.ecommerce.service.impl;

import com.ipn.mx.administracioneventos.ecommerce.domain.Carrito;
import com.ipn.mx.administracioneventos.ecommerce.domain.CarritoItem;
import com.ipn.mx.administracioneventos.ecommerce.domain.Producto;
import com.ipn.mx.administracioneventos.ecommerce.domain.Usuario;
import com.ipn.mx.administracioneventos.ecommerce.repository.CarritoItemRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.CarritoRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.ProductoRepository;
import com.ipn.mx.administracioneventos.ecommerce.repository.UsuarioRepository;
import com.ipn.mx.administracioneventos.ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class CarritoServiceImpl implements CarritoService {
    @Autowired
    private CarritoRepository carritoRepo;
    @Autowired
    private CarritoItemRepository itemRepo;
    @Autowired
    private UsuarioRepository usuarioRepo;
    @Autowired
    private ProductoRepository productoRepo;

    @Override
    @Transactional
    public Carrito obtenerOCrear(Long idUsuario) {
        return carritoRepo.findByUsuario_IdUsuarioAndActivoTrue(idUsuario).orElseGet(() -> {
            Usuario u = usuarioRepo.findById(idUsuario).orElseThrow();
            Carrito c = Carrito.builder()
                    .usuario(u)
                    .fechaCreacion(LocalDateTime.now())
                    .activo(true)
                    .items(new ArrayList<>())
                    .build();
            return carritoRepo.save(c);
        });
    }

    @Override
    @Transactional
    public Carrito agregarProducto(Long idUsuario, Long idProducto, Integer cantidad) {
        Carrito c = obtenerOCrear(idUsuario);
        Producto p = productoRepo.findById(idProducto).orElseThrow();
        CarritoItem item = CarritoItem.builder()
                .carrito(c)
                .producto(p)
                .cantidad(cantidad)
                .precioUnitario(p.getPrecio())
                .subtotal(p.getPrecio().multiply(BigDecimal.valueOf(cantidad)))
                .build();
        itemRepo.save(item);
        return carritoRepo.findById(c.getIdCarrito()).orElseThrow();
    }

    @Override
    @Transactional
    public Carrito eliminarProducto(Long idUsuario, Long idProducto) {
        Carrito c = obtenerOCrear(idUsuario);
        c.getItems().stream()
                .filter(i -> i.getProducto().getIdProducto().equals(idProducto))
                .findFirst()
                .ifPresent(i -> itemRepo.deleteById(i.getId()));
        return carritoRepo.findById(c.getIdCarrito()).orElseThrow();
    }

    @Override
    @Transactional
    public Carrito vaciar(Long idUsuario) {
        Carrito c = obtenerOCrear(idUsuario);
        c.getItems().forEach(i -> itemRepo.deleteById(i.getId()));
        return carritoRepo.findById(c.getIdCarrito()).orElseThrow();
    }

    @Override
    @Transactional(readOnly = true)
    public BigDecimal total(Long idUsuario) {
        Carrito c = obtenerOCrear(idUsuario);
        return c.getItems().stream()
                .map(CarritoItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}

