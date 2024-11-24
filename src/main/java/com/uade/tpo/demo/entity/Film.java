package com.uade.tpo.demo.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Entity
@Data
public class Film {
    public Film() {
        // Constructor por defecto requerido por JPA
    }

    public Film(String title, String category, String description,  Integer releaseYear, Integer duration, Double criticRating, int audienceRating, String posterPath, Integer budget, Long revenue) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.releaseYear = releaseYear;
        this.duration = duration;
        this.criticRating = criticRating;
        this.audienceRating = audienceRating;
        this.posterPath = posterPath;
        this.budget = budget;
        this.revenue = revenue;
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
    private int audienceRating;

    @Column
    private String posterPath;

    @Column
    private Integer budget;

    @Column
    private Long revenue;

    @OneToMany(mappedBy = "film", cascade = CascadeType.ALL)
    private List<Critic> critics = new ArrayList<>();

}
