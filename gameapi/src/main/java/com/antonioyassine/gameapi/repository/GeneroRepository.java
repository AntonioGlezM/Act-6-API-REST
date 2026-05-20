package com.antonioyassine.gameapi.repository;

import com.antonioyassine.gameapi.model.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositorio JPA para la entidad Genero
public interface GeneroRepository extends JpaRepository<Genero, Long> {

    // Busca géneros cuyo nombre contenga el texto, sin importar mayúsculas/minúsculas
    List<Genero> findByNombreContainingIgnoreCase(String nombre);
}
