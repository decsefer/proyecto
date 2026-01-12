package com.ipn.mx.administracioneventos.ecommerce.controller;

import com.ipn.mx.administracioneventos.ecommerce.domain.Pedido;
import com.ipn.mx.administracioneventos.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pedidos")
public class PedidoController {
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/generar")
    public ResponseEntity<?> generar(@RequestParam Long idUsuario) {
        return ResponseEntity.ok(pedidoService.generarPedido(idUsuario));
    }

    @GetMapping
    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(pedidoService.listar());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> obtener(@PathVariable Long id) {
        Pedido p = pedidoService.obtener(id);
        return (p == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(p);
    }

    @GetMapping("/{id}/total")
    public ResponseEntity<?> total(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.total(id));
    }

    @GetMapping(value = "/{id}/factura", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<?> factura(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.factura(id));
    }
}
