package com.ipn.mx.administracioneventos.ecommerce.service.impl;

import com.ipn.mx.administracioneventos.ecommerce.domain.Usuario;
import com.ipn.mx.administracioneventos.ecommerce.repository.UsuarioRepository;
import com.ipn.mx.administracioneventos.ecommerce.service.UsuarioService;
import com.ipn.mx.administracioneventos.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository repo;
    @Autowired(required = false)
    private EmailService emailService;

    @Override
    public Usuario registrar(Usuario u) {
        if (repo.findByEmail(u.getEmail()).isPresent()) {
            throw new IllegalStateException("Email ya registrado");
        }
        Usuario creado = repo.save(u);
        if (emailService != null && creado.getEmail() != null && !creado.getEmail().isEmpty()) {
            try {
                emailService.sendEmail(
                        creado.getEmail(),
                        "Confirmación de cuenta",
                        "Tu cuenta ha sido registrada exitosamente."
                );
            } catch (RuntimeException ignored) {
            }
        }
        return creado;
    }

    @Override
    public Usuario iniciarSesion(String email, String password) {
        return repo.findByEmail(email)
                .filter(u -> u.getPassword().equals(password))
                .orElse(null);
    }

    @Override
    public Usuario actualizarPerfil(Long id, Usuario cambios) {
        Usuario u = repo.findById(id).orElse(null);
        if (u == null) return null;
        u.setNombre(cambios.getNombre());
        u.setDireccion(cambios.getDireccion());
        return repo.save(u);
    }

    @Override
    public Usuario obtener(Long id) {
        return repo.findById(id).orElse(null);
    }

    @Override
    public List<Usuario> listar() {
        return repo.findAll();
    }
}
