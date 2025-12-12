package com.ipn.mx.administracioneventos.features.asistente.service.impl;

import com.ipn.mx.administracioneventos.core.domain.Asistente;
import com.ipn.mx.administracioneventos.features.asistente.repository.AsistenteRepository;
import com.ipn.mx.administracioneventos.features.asistente.service.AsistenteService;
import com.ipn.mx.administracioneventos.util.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class AsistenteServiceImpl implements AsistenteService {

    @Autowired
    private AsistenteRepository asistenteRepository;

    @Autowired(required = false)
    private EmailService emailService;

    @Override
    @Transactional(readOnly = true)
    public List<Asistente> findAll() {
        return asistenteRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Asistente findById(Long id) {
        return asistenteRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Asistente save(Asistente asistente) {
        boolean nuevo = asistente.getIdAsistente() == null;
        if (asistente.getFechaRegistro() == null) {
            asistente.setFechaRegistro(new Date());
        }
        Asistente res = asistenteRepository.save(asistente);
        if (nuevo && emailService != null && asistente.getEmail() != null && !asistente.getEmail().isEmpty()) {
            try {
                emailService.sendEmail(
                        asistente.getEmail(),
                        "Registro al evento",
                        "Tu registro como asistente ha sido confirmado."
                );
            } catch (RuntimeException ignored) {
            }
        }
        return res;
    }

    @Override
    @Transactional
    public void delete(Long id) {
        asistenteRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Asistente> findByEvento(Long idEvento) {
        return asistenteRepository.findByEvento_IdEvento(idEvento);
    }
}

