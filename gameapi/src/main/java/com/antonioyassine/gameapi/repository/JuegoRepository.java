package com.antonioyassine.gameapi.repository;

import com.antonioyassine.gameapi.model.Juego;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// Repositorio JPA para la entidad Juego
public interface JuegoRepository extends JpaRepository<Juego, Long> {

    // Busca juegos cuyo título contenga el texto, sin importar mayúsculas/minúsculas
    List<Juego> findByTituloContainingIgnoreCase(String titulo);

    // Busca juegos con precio menor o igual al indicado
    List<Juego> findByPrecioLessThanEqual(Double precio);

    // Busca los juegos de un estudio concreto
    List<Juego> findByEstudioId(Long estudioId);

    // JPQL: cuenta cuántos juegos tiene un género determinado (navega la relación ManyToMany)
    @Query("SELECT COUNT(j) FROM Juego j JOIN j.generos g WHERE g.id = :generoId")
    Long contarJuegosPorGenero(@Param("generoId") Long generoId);

    // JPQL: obtiene los juegos que pertenecen a un género concreto
    @Query("SELECT j FROM Juego j JOIN j.generos g WHERE g.id = :generoId")
    List<Juego> findByGeneroId(@Param("generoId") Long generoId);
}
