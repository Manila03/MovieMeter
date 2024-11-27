package com.uade.tpo.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
public class Critic {
    public Critic() {
        // Constructor por defecto requerido por JPA
    }

    public Critic(String description, int value,User user, Film film) {
        this.description = description;
        this.value = value;
        this.user = user;
        this.film = film;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private int value;

    @Column
    private String description;

    @JsonBackReference // Indica que esta propiedad no debe serializarse en profundidad
    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    @ToString.Exclude
    private Film film;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @ToString.Exclude
    private User user;

    
    // this is the critic made from a user rating a particular film,
    // later this will modifiy the film.rating itself
}
