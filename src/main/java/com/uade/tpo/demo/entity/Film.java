package com.uade.tpo.demo.entity;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import info.movito.themoviedbapi.model.movies.Cast;
import info.movito.themoviedbapi.model.movies.Credits;
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
public class Film {
    public Film() {
        // Constructor por defecto requerido por JPA
    }

    public Film(String title, String category, String description,  Integer releaseYear, Integer duration, Double criticRating, Integer audienceRating, String posterPath, Integer budget, Long revenue, Integer voteCount) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.audienceRating = audienceRating;
        this.criticRating = criticRating;
        this.posterPath = posterPath;
        this.budget = budget;
        this.revenue = revenue;
        this.voteCount = voteCount;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //title.basics
    @Column
    private String imdbId; //index: 0

    // pelicula o serie
    @Column
    private String type; // index: 1
    
    @Column
    private String title; // index: 2

    @Column
    private Integer releaseYear; // index: 5
    
    @Column
    private Integer duration; // index: 7

    @Column
    private String category; // index: 8

    //title.rating
    @Column
    private Double criticRating; // index: 1

    @Column
    private Integer voteCount; // index: 2

    // ---------------------------

    //tmdb API
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Integer audienceRating;

    @Column
    private String posterPath;

    @Column
    private Integer budget;

    @Column
    private Long revenue;

    @JsonManagedReference // Indica que esta lista es la "gestora" en la relación
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Critic> critics = new ArrayList<>();

    @ManyToMany(cascade = CascadeType.MERGE)
    @JsonManagedReference // Evita ciclos JSON, indica lado propietario
    @ToString.Exclude
    @JoinTable(
        name = "actor_film",
        joinColumns = @JoinColumn(name = "film_id"),
        inverseJoinColumns = @JoinColumn(name = "actor_id")
    )
    private List<Actor> actors = new ArrayList<>();
}
