package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

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

    @ManyToOne
    @JoinColumn(name = "film_id", nullable = false)
    private Film film;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    
    // this is the critic made from a user rating a particular film,
    // later this will modifiy the film.rating itself
}
