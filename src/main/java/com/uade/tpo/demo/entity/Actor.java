package com.uade.tpo.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Actor {
    public Actor() {
        // Constructor por defecto requerido por JPA
    }

    public Actor(String name, String imdbNameId, String picture) {
        this.name = name;
        this.imdbNameId = imdbNameId;
        // this.birthYear = birthYear;
        // this.deathYear = deathYear;
        this.picture = picture;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(unique = true, nullable = false)
    private String imdbNameId;

    @Column
    private String picture;

    @ManyToMany(mappedBy = "actors")
    @JsonBackReference // Evita ciclos al serializar
    @ToString.Exclude
    private List<Film> filmsActed = new ArrayList<>();

    // el aspecto 'knownFor' lo voy a hacer manualmente cuando ya tenga todas las tablas, eligiendo las 4 peliculas con mejor rating de cada actor
}
