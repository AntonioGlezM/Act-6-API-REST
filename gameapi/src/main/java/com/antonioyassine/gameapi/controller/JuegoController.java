package com.antonioyassine.gameapi.controller;

import com.antonioyassine.gameapi.model.Juego;
import com.antonioyassine.gameapi.model.Genero;
import com.antonioyassine.gameapi.service.JuegoService;
import com.antonioyassine.gameapi.service.GeneroService;
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

// Controlador REST para gestionar videojuegos
@RestController
@RequestMapping("/api/v1/juegos")
public class JuegoController {

    private final JuegoService juegoService;
    private final GeneroService generoService;

    // Inyección por constructor
    public JuegoController(JuegoService juegoService, GeneroService generoService) {
        this.juegoService = juegoService;
        this.generoService = generoService;
    }

    // GET /api/v1/juegos - Obtiene todos los juegos
    @GetMapping
    public ResponseEntity<List<Juego>> getAll() {
        return ResponseEntity.ok(juegoService.findAll());
    }

    // GET /api/v1/juegos/{id} - Obtiene un juego por ID
    @GetMapping("/{id}")
    public ResponseEntity<Juego> getById(@PathVariable Long id) {
        return juegoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/juegos - Crea un nuevo juego
    @PostMapping
    public ResponseEntity<Juego> create(@Valid @RequestBody Juego juego) {
        Juego guardado = juegoService.save(juego);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/v1/juegos/{id} - Actualiza un juego existente
    @PutMapping("/{id}")
    public ResponseEntity<Juego> update(@PathVariable Long id, @Valid @RequestBody Juego juego) {
        return juegoService.update(id, juego)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/juegos/{id} - Elimina un juego
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (juegoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/v1/juegos/buscar?titulo=...&precioMax=...&sortBy=id&order=asc (Módulo B)
    @GetMapping("/buscar")
    public ResponseEntity<List<Juego>> search(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) Double precioMax,
            @RequestParam(required = false, defaultValue = "id") String sortBy,
            @RequestParam(required = false, defaultValue = "asc") String order) {

        if (titulo != null) {
            return ResponseEntity.ok(juegoService.searchByTitulo(titulo));
        }
        if (precioMax != null) {
            return ResponseEntity.ok(juegoService.searchByPrecioMax(precioMax));
        }
        // Si no hay filtro, devuelve todos ordenados
        return ResponseEntity.ok(juegoService.findAllSorted(sortBy, order));
    }

    // GET /api/v1/juegos/genero/{generoId} - Juegos de un género concreto (Módulo C)
    @GetMapping("/genero/{generoId}")
    public ResponseEntity<List<Juego>> getByGenero(@PathVariable Long generoId) {
        return ResponseEntity.ok(juegoService.findByGeneroId(generoId));
    }

    // GET /api/v1/juegos/genero/{generoId}/count - Cuenta juegos de un género (Módulo C)
    @GetMapping("/genero/{generoId}/count")
    public ResponseEntity<Long> countByGenero(@PathVariable Long generoId) {
        return ResponseEntity.ok(juegoService.contarJuegosPorGenero(generoId));
    }

    // PUT /api/v1/juegos/{id}/generos - Asigna géneros a un juego (Módulo C)
    @PutMapping("/{id}/generos")
    public ResponseEntity<Juego> assignGeneros(@PathVariable Long id, @RequestBody List<Long> generoIds) {
        return juegoService.findById(id).map(juego -> {
            // Busca los géneros por sus IDs y los asigna al juego
            List<Genero> generos = generoIds.stream()
                    .map(generoService::findById)
                    .filter(java.util.Optional::isPresent)
                    .map(java.util.Optional::get)
                    .toList();
            juego.setGeneros(generos);
            return ResponseEntity.ok(juegoService.save(juego));
        }).orElse(ResponseEntity.notFound().build());
    }
}
