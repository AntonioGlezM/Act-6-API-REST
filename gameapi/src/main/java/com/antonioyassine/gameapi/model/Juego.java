package com.antonioyassine.gameapi.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

// Entidad que representa un videojuego
@Entity
@Table(name = "juegos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Juego {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El título del juego no puede estar vacío")
    @Size(max = 150, message = "El título no puede superar los 150 caracteres")
    @Column(nullable = false)
    private String titulo;

    @Size(max = 500, message = "La descripción no puede superar los 500 caracteres")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor que 0")
    @Column(nullable = false)
    private Double precio;

    @Column(name = "fecha_lanzamiento")
    private LocalDate fechaLanzamiento;

    // Muchos juegos pertenecen a un estudio (FK: estudio_id)
    @ManyToOne
    @JoinColumn(name = "estudio_id")
    private Estudio estudio;

    // Un juego puede tener varios géneros y un género puede estar en varios juegos
    @ManyToMany
    @JoinTable(
            name = "juego_genero",
            joinColumns = @JoinColumn(name = "juego_id"),
            inverseJoinColumns = @JoinColumn(name = "genero_id")
    )
    @ToString.Exclude
    private List<Genero> generos;
}
