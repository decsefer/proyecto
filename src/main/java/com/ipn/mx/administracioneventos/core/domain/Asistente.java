package com.ipn.mx.administracioneventos.core.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Asistente", schema = "public")
public class Asistente implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsistente;

    @NotBlank(message = "No puede estar en blnco")
    @Size(min = 2, max = 50, message = "El valor debera estar entre 2 y 50")
    @Column(name = "nombre",length = 50, nullable = false)
    private String nombre;

    @Column(name = "paterno",length = 50, nullable = false)
    private String paterno;
    @Size(min = 2, max = 50, message = "El valor debera estar entre 2 y 50")
    @Column(name = "materno",length = 50, nullable = false)
    @Size(min = 2, max = 50, message = "El valor debera estar entre 2 y 50")
    private String materno;

    @Email(message = "Escribe un correo electronico valido")
    @Column(name = "email",length = 150, nullable = false)
    private String email;

    @Temporal(TemporalType.DATE)
    @Column(name = "fechaRegistro", nullable = false)
    private Date fechaRegistro;

    @ManyToOne
    @JoinColumn(name = "idEvento", nullable = false)
    @JsonBackReference
    private Evento evento;


    //Codificar para que cuando se registre un asistente le llegue un correo
}
