package com.ipn.mx.administracioneventos.features.asistente.repository;

import com.ipn.mx.administracioneventos.core.domain.Asistente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AsistenteRepository extends JpaRepository<Asistente, Long> {
    List<Asistente> findByEvento_IdEvento(Long idEvento);
}

