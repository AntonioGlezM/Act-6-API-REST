package com.antonioyassine.gameapi.controller;

import com.antonioyassine.gameapi.model.Estudio;
import com.antonioyassine.gameapi.model.Juego;
import com.antonioyassine.gameapi.service.EstudioService;
import com.antonioyassine.gameapi.service.JuegoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// Controlador REST para gestionar estudios de videojuegos
@RestController
@RequestMapping("/api/v1/estudios")
public class EstudioController {

    private final EstudioService estudioService;
    private final JuegoService juegoService;

    // Inyección por constructor de los servicios necesarios
    public EstudioController(EstudioService estudioService, JuegoService juegoService) {
        this.estudioService = estudioService;
        this.juegoService = juegoService;
    }

    // GET /api/v1/estudios - Obtiene todos los estudios
    @GetMapping
    public ResponseEntity<List<Estudio>> getAll() {
        return ResponseEntity.ok(estudioService.findAll());
    }

    // GET /api/v1/estudios/{id} - Obtiene un estudio por ID
    @GetMapping("/{id}")
    public ResponseEntity<Estudio> getById(@PathVariable Long id) {
        return estudioService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/estudios - Crea un nuevo estudio
    @PostMapping
    public ResponseEntity<Estudio> create(@Valid @RequestBody Estudio estudio) {
        Estudio guardado = estudioService.save(estudio);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/v1/estudios/{id} - Actualiza un estudio existente
    @PutMapping("/{id}")
    public ResponseEntity<Estudio> update(@PathVariable Long id, @Valid @RequestBody Estudio estudio) {
        return estudioService.update(id, estudio)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/estudios/{id} - Elimina un estudio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (estudioService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/v1/estudios/{id}/juegos - Obtiene los juegos de un estudio (Módulo A)
    @GetMapping("/{id}/juegos")
    public ResponseEntity<List<Juego>> getJuegosByEstudio(@PathVariable Long id) {
        return estudioService.findById(id)
                .map(estudio -> ResponseEntity.ok(juegoService.findByEstudioId(id)))
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/v1/estudios/buscar?nombre=...&pais=... - Búsqueda con parámetros opcionales (Módulo B)
    @GetMapping("/buscar")
    public ResponseEntity<List<Estudio>> search(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String pais) {

        if (nombre != null) {
            return ResponseEntity.ok(estudioService.searchByNombre(nombre));
        }
        if (pais != null) {
            return ResponseEntity.ok(estudioService.searchByPais(pais));
        }
        // Si no se pasa ningún parámetro, devuelve todos
        return ResponseEntity.ok(estudioService.findAll());
    }
}
