package com.ipn.mx.administracioneventos.ecommerce.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUsuario;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String nombre;

    @Email
    @NotBlank
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @NotBlank
    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 150)
    private String direccion;
}

