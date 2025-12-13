package com.ipn.mx.administracioneventos.ecommerce.controller;

import com.ipn.mx.administracioneventos.ecommerce.dto.CarritoItemRequest;
import com.ipn.mx.administracioneventos.ecommerce.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/usuarios/{idUsuario}/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;

    @GetMapping
    public ResponseEntity<?> obtener(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(carritoService.obtenerOCrear(idUsuario));
    }

    @PostMapping("/items")
    public ResponseEntity<?> agregar(@PathVariable Long idUsuario, @RequestBody CarritoItemRequest body) {
        return ResponseEntity.ok(carritoService.agregarProducto(idUsuario, body.getIdProducto(), body.getCantidad()));
    }

    @DeleteMapping("/items/{idProducto}")
    public ResponseEntity<?> eliminar(@PathVariable Long idUsuario, @PathVariable Long idProducto) {
        return ResponseEntity.ok(carritoService.eliminarProducto(idUsuario, idProducto));
    }

    @DeleteMapping("/items")
    public ResponseEntity<?> vaciar(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(carritoService.vaciar(idUsuario));
    }

    @GetMapping("/total")
    public ResponseEntity<?> total(@PathVariable Long idUsuario) {
        return ResponseEntity.ok(carritoService.total(idUsuario));
    }
}

