package com.ipn.mx.administracioneventos.core.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
@Table(name = "Evento", schema = "public")

public class Evento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "idEvento", nullable = false)
    private Long idEvento;

    @NotEmpty(message = "No puede estar vacio")
    @Column(name = "nombreEvento", length=200, nullable = false, unique = true)
    private String nombreEvento;

    @NotEmpty(message = "No puede estar vacia")
    @Column(name = "descripcionEvento", length=500, nullable = false)
    private String descripcionEvento;

    @NotNull(message = "La fecha no puede ser nula")
    @FutureOrPresent(message = "La fecha de inicio debera ser hoy o cualquier fecha en el futuro")
    @Temporal(TemporalType.DATE)
    private Date fechaInicio;

    @NotNull(message = "La fecha no puede ser nula")
    private Date fechaFin;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "evento")
    @JsonManagedReference
    private List<Asistente> asistentes;

}
