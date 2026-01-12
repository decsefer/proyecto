package com.ipn.mx.administracioneventos.ecommerce.repository;

import com.ipn.mx.administracioneventos.ecommerce.domain.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    List<CarritoItem> findByCarrito_IdCarrito(Long idCarrito);
    Optional<CarritoItem> findByCarrito_IdCarritoAndProducto_IdProducto(Long idCarrito, Long idProducto);
    @Transactional
    void deleteByCarrito_IdCarritoAndProducto_IdProducto(Long idCarrito, Long idProducto);
    @Transactional
    void deleteByCarrito_IdCarrito(Long idCarrito);
}
