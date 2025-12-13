package com.ipn.mx.administracioneventos.ecommerce.controller;

import com.ipn.mx.administracioneventos.ecommerce.domain.Usuario;
import com.ipn.mx.administracioneventos.ecommerce.service.PedidoService;
import com.ipn.mx.administracioneventos.ecommerce.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;
    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/usuarios/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario u) {
        return ResponseEntity.ok(usuarioService.registrar(u));
    }

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestParam String email, @RequestParam String password) {
        Usuario u = usuarioService.iniciarSesion(email, password);
        return (u == null) ? ResponseEntity.status(401).build() : ResponseEntity.ok(u);
    }

    @GetMapping("/usuarios/{id}")
    public ResponseEntity<?> perfil(@PathVariable Long id) {
        Usuario u = usuarioService.obtener(id);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    @PutMapping("/usuarios/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Long id, @RequestBody Usuario cambios) {
        Usuario u = usuarioService.actualizarPerfil(id, cambios);
        return (u == null) ? ResponseEntity.notFound().build() : ResponseEntity.ok(u);
    }

    @GetMapping("/usuarios/{id}/pedidos")
    public ResponseEntity<?> pedidosUsuario(@PathVariable Long id) {
        return ResponseEntity.ok(pedidoService.listarPorUsuario(id));
    }
}

