package com.ipn.mx.administracioneventos.ecommerce.controller;

import com.ipn.mx.administracioneventos.ecommerce.domain.Categoria;
import com.ipn.mx.administracioneventos.ecommerce.service.CategoriaService;
import com.ipn.mx.administracioneventos.ecommerce.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaService categoriaService;
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(categoriaService.listar());
    }

    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Categoria c) {
        return ResponseEntity.ok(categoriaService.crear(c));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id) {
        Categoria c = categoriaService.obtener(id);
        return (c == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(c);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Categoria c) {
        Categoria x = categoriaService.actualizar(id, c);
        return (x == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(x);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        categoriaService.eliminar(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/productos")
    public ResponseEntity<?> productosPorCategoria(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.listar(id, null, null, null));
    }
}

