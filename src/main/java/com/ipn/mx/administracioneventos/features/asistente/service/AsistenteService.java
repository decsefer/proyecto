package com.ipn.mx.administracioneventos.features.asistente.service;

import com.ipn.mx.administracioneventos.core.domain.Asistente;

import java.util.List;

public interface AsistenteService {
    List<Asistente> findAll();
    Asistente findById(Long id);
    Asistente save(Asistente asistente);
    void delete(Long id);
    List<Asistente> findByEvento(Long idEvento);
}

