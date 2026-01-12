package com.ipn.mx.administracioneventos.features.evento.controller;


import com.ipn.mx.administracioneventos.core.domain.Evento;
import com.ipn.mx.administracioneventos.features.evento.service.EventoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = {"http://localhost:8080", "https://*.onrender.com", "https://*.netlify.app"})
@RestController
@RequestMapping("/api/v1/eventos")

public class EventoController {

    @Autowired
    private EventoService service;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Evento> readAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> readById(@PathVariable Long id) {
        Evento evento = null;
        Map<String, Object> respuesta = new HashMap<>();
        try {
            evento = service.findById(id);
        }
        catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al realizar el registro en la base de datos");
            respuesta.put("error", ex.getMessage().concat(": ").concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }

        if (evento == null) {
            respuesta.put("mensaje", "El evento con ide". concat(id.toString().concat("No esxitse en la base de datos")));
//            return new ResponseEntity<Map<String>, Object>(respuesta,HttpStatus.NOT_FOUND);
         return new ResponseEntity<>(respuesta, HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<Evento>(evento, HttpStatus.OK);



    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody Evento evento) {
        Evento e = null;
        Map<String, Object> respuesta = new HashMap<>();
        try{
            e = service.save(evento);
        }catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al insertar el registro en la base de datos");
            respuesta.put("error", ex.getMessage().concat(": ")
                    .concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El evento se ha creado con exito");
        respuesta.put("evento", e);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Evento evento, @PathVariable Long id) {
        Evento e = service.findById(id);
        Evento eventoActializado = null;
        Map<String, Object> respuesta = new HashMap<>();
        if (e == null) {
            respuesta.put("mensaje", "Error: no se pudo editar, el evento con id"
                    .concat(id.toString().concat("No existe en la base de datos")));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.NOT_FOUND);
        }
        try{

            e.setNombreEvento(evento.getNombreEvento());
            e.setDescripcionEvento(evento.getDescripcionEvento());
            e.setFechaInicio(evento.getFechaInicio());
            e.setFechaFin(evento.getFechaFin());

            eventoActializado = service.save(e);
        }catch (DataAccessException ex) {
            respuesta.put("mensaje", "Error al insertar el evento en la base de datos");
            respuesta.put("error", ex.getMessage().concat(": ")
                    .concat(ex.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        respuesta.put("mensaje", "El evento se ha actualizado con exito");
        respuesta.put("evento", eventoActializado);
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    //Antes de eliminar verificar que el registro exista, despues borrar
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Map<String, Object> respuesta = new HashMap<>();
        try {
            service.delete(id);
        }
        catch (DataAccessException e) {
            respuesta.put("mensaje", "Error al eliminar el registro en la base de datos");
            respuesta.put("error", e.getMessage().concat(": ").concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);

        }
        respuesta.put("mensaje", "El evento se ha eliminado con exito");
        return new ResponseEntity<Map<String, Object>>(respuesta, HttpStatus.OK);
    }

}
