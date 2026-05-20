package com.antonioyassine.gameapi.service;

import com.antonioyassine.gameapi.model.Juego;
import com.antonioyassine.gameapi.repository.JuegoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio que contiene la lógica de negocio para Juego
@Service
public class JuegoService {

    private final JuegoRepository juegoRepository;

    // Inyección de dependencias por constructor
    public JuegoService(JuegoRepository juegoRepository) {
        this.juegoRepository = juegoRepository;
    }

    // Devuelve todos los juegos
    public List<Juego> findAll() {
        return juegoRepository.findAll();
    }

    // Devuelve todos los juegos ordenados dinámicamente
    public List<Juego> findAllSorted(String sortBy, String order) {
        Sort sort = order.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();
        return juegoRepository.findAll(sort);
    }

    // Busca un juego por ID
    public Optional<Juego> findById(Long id) {
        return juegoRepository.findById(id);
    }

    // Guarda un nuevo juego
    public Juego save(Juego juego) {
        return juegoRepository.save(juego);
    }

    // Actualiza un juego existente
    public Optional<Juego> update(Long id, Juego juegoActualizado) {
        return juegoRepository.findById(id).map(juego -> {
            juego.setTitulo(juegoActualizado.getTitulo());
            juego.setDescripcion(juegoActualizado.getDescripcion());
            juego.setPrecio(juegoActualizado.getPrecio());
            juego.setFechaLanzamiento(juegoActualizado.getFechaLanzamiento());
            juego.setEstudio(juegoActualizado.getEstudio());
            juego.setGeneros(juegoActualizado.getGeneros());
            return juegoRepository.save(juego);
        });
    }

    // Elimina un juego por ID
    public boolean deleteById(Long id) {
        if (juegoRepository.existsById(id)) {
            juegoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Busca juegos por título (parcial, ignora mayúsculas)
    public List<Juego> searchByTitulo(String titulo) {
        return juegoRepository.findByTituloContainingIgnoreCase(titulo);
    }

    // Busca juegos con precio menor o igual
    public List<Juego> searchByPrecioMax(Double precioMax) {
        return juegoRepository.findByPrecioLessThanEqual(precioMax);
    }

    // Busca juegos de un estudio concreto
    public List<Juego> findByEstudioId(Long estudioId) {
        return juegoRepository.findByEstudioId(estudioId);
    }

    // Busca juegos de un género concreto (usa @Query JPQL)
    public List<Juego> findByGeneroId(Long generoId) {
        return juegoRepository.findByGeneroId(generoId);
    }

    // Cuenta los juegos de un género (usa @Query JPQL)
    public Long contarJuegosPorGenero(Long generoId) {
        return juegoRepository.contarJuegosPorGenero(generoId);
    }
}
