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
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Film {
    public Film() {
        // Constructor por defecto requerido por JPA
    }

    public Film(String title, String category, String description,  Integer releaseYear, Integer duration, Double criticRating, Integer audienceRating, String posterPath, Integer budget, Long revenue, Integer voteCount, Double popularity) {
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
        this.popularity = popularity;
    }


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String category;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column
    private Integer releaseYear;

    @Column
    private Integer duration; // Duración en minutos

    @Column
    private Double criticRating;

    @Column
    private Integer audienceRating;

    @Column
    private String posterPath;

    @Column
    private Integer budget;

    @Column
    private Long revenue;

    @Column
    private Integer voteCount;

    @Column
    private Double popularity;

    @JsonManagedReference // Indica que esta lista es la "gestora" en la relación
    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    @ToString.Exclude
    private List<Critic> critics = new ArrayList<>();

}
