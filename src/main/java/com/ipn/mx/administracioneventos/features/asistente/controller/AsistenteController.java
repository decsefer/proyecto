package com.ipn.mx.administracioneventos.features.asistente.controller;

import com.ipn.mx.administracioneventos.core.domain.Asistente;
import com.ipn.mx.administracioneventos.core.domain.Evento;
import com.ipn.mx.administracioneventos.features.asistente.service.AsistenteService;
import com.ipn.mx.administracioneventos.features.evento.service.EventoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(originPatterns = {"http://localhost:*", "https://*.onrender.com", "https://*.netlify.app"})
@RestController
@RequestMapping("/api/v1")
public class AsistenteController {

    @Autowired
    private AsistenteService asistenteService;

    @Autowired
    private EventoService eventoService;

    @GetMapping("/asistentes")
    @ResponseStatus(HttpStatus.OK)
    public List<Asistente> readAll() {
        return asistenteService.findAll();
    }

    @GetMapping("/asistentes/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Asistente a = asistenteService.findById(id);
            if (a == null) {
                respuesta.put("mensaje", "El asistente con id " + id + " no existe en la base de datos");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(a, HttpStatus.OK);
        } catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al consultar en la base de datos");
            respuesta.put("error", ex.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/eventos/{idEvento}/asistentes")
    public ResponseEntity<?> readByEvento(@PathVariable Long idEvento) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Evento evento = eventoService.findById(idEvento);
            if (evento == null) {
                respuesta.put("mensaje", "El evento con id " + idEvento + " no existe en la base de datos");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
            List<Asistente> lista = asistenteService.findByEvento(idEvento);
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al consultar en la base de datos");
            respuesta.put("error", ex.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/eventos/{idEvento}/asistentes")
    public ResponseEntity<?> createForEvento(@PathVariable Long idEvento, @Valid @RequestBody Asistente asistente) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Evento evento = eventoService.findById(idEvento);
            if (evento == null) {
                respuesta.put("mensaje", "El evento con id " + idEvento + " no existe en la base de datos");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
            asistente.setEvento(evento);
            asistente.setFechaRegistro(new Date());
            Asistente creado = asistenteService.save(asistente);
            respuesta.put("mensaje", "El asistente se ha creado con exito");
            respuesta.put("asistente", creado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al insertar el registro en la base de datos");
            respuesta.put("error", ex.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/asistentes/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody Asistente asistente) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            Asistente actual = asistenteService.findById(id);
            if (actual == null) {
                respuesta.put("mensaje", "El asistente con id " + id + " no existe en la base de datos");
                return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);
            }
            actual.setNombre(asistente.getNombre());
            actual.setPaterno(asistente.getPaterno());
            actual.setMaterno(asistente.getMaterno());
            actual.setEmail(asistente.getEmail());
            Asistente actualizado = asistenteService.save(actual);
            respuesta.put("mensaje", "El asistente se ha actualizado con exito");
            respuesta.put("asistente", actualizado);
            return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
        } catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al actualizar el registro en la base de datos");
            respuesta.put("error", ex.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/asistentes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            asistenteService.delete(id);
            respuesta.put("mensaje", "El asistente se ha eliminado con exito");
            return new ResponseEntity<>(respuesta, HttpStatus.OK);
        } catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al eliminar el registro en la base de datos");
            respuesta.put("error", ex.getMessage());
            return new ResponseEntity<>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

