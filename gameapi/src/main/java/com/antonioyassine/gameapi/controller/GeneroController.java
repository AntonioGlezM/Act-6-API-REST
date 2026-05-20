package com.antonioyassine.gameapi.controller;

import com.antonioyassine.gameapi.model.Genero;
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

// Controlador REST para gestionar géneros de videojuegos
@RestController
@RequestMapping("/api/v1/generos")
public class GeneroController {

    private final GeneroService generoService;

    // Inyección por constructor
    public GeneroController(GeneroService generoService) {
        this.generoService = generoService;
    }

    // GET /api/v1/generos - Obtiene todos los géneros
    @GetMapping
    public ResponseEntity<List<Genero>> getAll() {
        return ResponseEntity.ok(generoService.findAll());
    }

    // GET /api/v1/generos/{id} - Obtiene un género por ID
    @GetMapping("/{id}")
    public ResponseEntity<Genero> getById(@PathVariable Long id) {
        return generoService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/v1/generos - Crea un nuevo género
    @PostMapping
    public ResponseEntity<Genero> create(@Valid @RequestBody Genero genero) {
        Genero guardado = generoService.save(genero);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // PUT /api/v1/generos/{id} - Actualiza un género existente
    @PutMapping("/{id}")
    public ResponseEntity<Genero> update(@PathVariable Long id, @Valid @RequestBody Genero genero) {
        return generoService.update(id, genero)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE /api/v1/generos/{id} - Elimina un género
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (generoService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/v1/generos/buscar?nombre=... - Búsqueda por nombre (Módulo B)
    @GetMapping("/buscar")
    public ResponseEntity<List<Genero>> search(
            @RequestParam(required = false) String nombre) {

        if (nombre != null) {
            return ResponseEntity.ok(generoService.searchByNombre(nombre));
        }
        return ResponseEntity.ok(generoService.findAll());
    }
}
