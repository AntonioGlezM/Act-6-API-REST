package com.antonioyassine.gameapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

// Entidad que representa un estudio de desarrollo de videojuegos
@Entity
@Table(name = "estudios")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Estudio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del estudio no puede estar vacío")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "El país no puede estar vacío")
    @Size(max = 60, message = "El país no puede superar los 60 caracteres")
    @Column(nullable = false)
    private String pais;

    @NotNull(message = "El año de fundación es obligatorio")
    @Column(name = "anio_fundacion", nullable = false)
    private Integer anioFundacion;

    // Un estudio tiene muchos juegos. Se ignora en JSON para evitar recursión infinita
    @OneToMany(mappedBy = "estudio", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    private List<Juego> juegos;
}
