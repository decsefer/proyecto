package com.ipn.mx.administracioneventos.ecommerce.service;

import com.ipn.mx.administracioneventos.ecommerce.domain.Usuario;

import java.util.List;

public interface UsuarioService {
    Usuario registrar(Usuario u);
    Usuario iniciarSesion(String email, String password);
    Usuario actualizarPerfil(Long id, Usuario cambios);
    Usuario obtener(Long id);
    List<Usuario> listar();
}

