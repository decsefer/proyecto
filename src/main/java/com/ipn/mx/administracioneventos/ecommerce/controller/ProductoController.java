package com.ipn.mx.administracioneventos.ecommerce.controller;

import com.ipn.mx.administracioneventos.ecommerce.domain.Producto;
import com.ipn.mx.administracioneventos.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> listar(@RequestParam(required = false) Long categoria,
                                    @RequestParam(required = false) String q,
                                    @RequestParam(required = false) BigDecimal minPrecio,
                                    @RequestParam(required = false) BigDecimal maxPrecio) {
        return ResponseEntity.ok(productoService.listar(categoria, q, minPrecio, maxPrecio));
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Producto p) {
        return ResponseEntity.ok(productoService.crear(p));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> mostrar(@PathVariable Long id) {
        Producto p = productoService.obtener(id);
        return (p == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Producto p) {
        Producto x = productoService.actualizar(id, p);
        return (x == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(x);
    }

    @PatchMapping("/{id}/stock")
    public ResponseEntity<?> stock(@PathVariable Long id, @RequestParam Integer stock) {
        Producto x = productoService.actualizarStock(id, stock);
        return (x == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(x);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        productoService.eliminar(id);
        return ResponseEntity.ok().build();
    }
}

