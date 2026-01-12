package com.ipn.mx.administracioneventos.ecommerce.controller;

import com.ipn.mx.administracioneventos.ecommerce.domain.Pedido;
import com.ipn.mx.administracioneventos.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:4200", "https://proyecto-z9eq.onrender.com", "https://proyecto-angularfin.netlify.app"}, allowCredentials = "true")
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

    @GetMapping(value = "/{id}/factura", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<?> factura(@PathVariable Long id) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=factura_" + id + ".pdf")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pedidoService.factura(id));
    }
}
