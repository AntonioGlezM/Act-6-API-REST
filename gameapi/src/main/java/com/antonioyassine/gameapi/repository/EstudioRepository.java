package com.antonioyassine.gameapi.repository;

import com.antonioyassine.gameapi.model.Estudio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// Repositorio JPA para la entidad Estudio
public interface EstudioRepository extends JpaRepository<Estudio, Long> {

    // Busca estudios cuyo nombre contenga el texto, sin importar mayúsculas/minúsculas
    List<Estudio> findByNombreContainingIgnoreCase(String nombre);

    // Busca estudios por país, sin importar mayúsculas/minúsculas
    List<Estudio> findByPaisIgnoreCase(String pais);
}
