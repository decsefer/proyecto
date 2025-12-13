package com.ipn.mx.administracioneventos.ecommerce.repository;

import com.ipn.mx.administracioneventos.ecommerce.domain.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {}

