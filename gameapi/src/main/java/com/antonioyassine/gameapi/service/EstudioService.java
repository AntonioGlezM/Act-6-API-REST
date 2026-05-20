package com.antonioyassine.gameapi.service;

import com.antonioyassine.gameapi.model.Estudio;
import com.antonioyassine.gameapi.repository.EstudioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Servicio que contiene la lógica de negocio para Estudio
@Service
public class EstudioService {

    private final EstudioRepository estudioRepository;

    // Inyección de dependencias por constructor (recomendado sobre @Autowired)
    public EstudioService(EstudioRepository estudioRepository) {
        this.estudioRepository = estudioRepository;
    }

    // Devuelve todos los estudios
    public List<Estudio> findAll() {
        return estudioRepository.findAll();
    }

    // Busca un estudio por su ID. Devuelve Optional para manejar el caso de no encontrado
    public Optional<Estudio> findById(Long id) {
        return estudioRepository.findById(id);
    }

    // Guarda un nuevo estudio en la base de datos
    public Estudio save(Estudio estudio) {
        return estudioRepository.save(estudio);
    }

    // Actualiza un estudio existente. Devuelve Optional vacío si no existe
    public Optional<Estudio> update(Long id, Estudio estudioActualizado) {
        return estudioRepository.findById(id).map(estudio -> {
            estudio.setNombre(estudioActualizado.getNombre());
            estudio.setPais(estudioActualizado.getPais());
            estudio.setAnioFundacion(estudioActualizado.getAnioFundacion());
            return estudioRepository.save(estudio);
        });
    }

    // Elimina un estudio por ID. Devuelve true si existía y se borró
    public boolean deleteById(Long id) {
        if (estudioRepository.existsById(id)) {
            estudioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Busca estudios por nombre (parcial, ignora mayúsculas)
    public List<Estudio> searchByNombre(String nombre) {
        return estudioRepository.findByNombreContainingIgnoreCase(nombre);
    }

    // Busca estudios por país
    public List<Estudio> searchByPais(String pais) {
        return estudioRepository.findByPaisIgnoreCase(pais);
    }
}
