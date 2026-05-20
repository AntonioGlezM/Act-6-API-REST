package com.antonioyassine.gameapi.service;

import com.antonioyassine.gameapi.model.Genero;
import com.antonioyassine.gameapi.repository.GeneroRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio que contiene la lógica de negocio para Genero
@Service
public class GeneroService {

    private final GeneroRepository generoRepository;

    // Inyección de dependencias por constructor
    public GeneroService(GeneroRepository generoRepository) {
        this.generoRepository = generoRepository;
    }

    // Devuelve todos los géneros
    public List<Genero> findAll() {
        return generoRepository.findAll();
    }

    // Busca un género por ID
    public Optional<Genero> findById(Long id) {
        return generoRepository.findById(id);
    }

    // Guarda un nuevo género
    public Genero save(Genero genero) {
        return generoRepository.save(genero);
    }

    // Actualiza un género existente
    public Optional<Genero> update(Long id, Genero generoActualizado) {
        return generoRepository.findById(id).map(genero -> {
            genero.setNombre(generoActualizado.getNombre());
            genero.setDescripcion(generoActualizado.getDescripcion());
            return generoRepository.save(genero);
        });
    }

    // Elimina un género por ID
    public boolean deleteById(Long id) {
        if (generoRepository.existsById(id)) {
            generoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Busca géneros por nombre (parcial, ignora mayúsculas)
    public List<Genero> searchByNombre(String nombre) {
        return generoRepository.findByNombreContainingIgnoreCase(nombre);
    }
}
