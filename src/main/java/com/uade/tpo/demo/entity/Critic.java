package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Critic {
    public Critic() {
        // Constructor por defecto requerido por JPA
    }

    public Critic(String description, int value) {
        this.description = description;
        this.value = value;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private int value;

    @Column
    private String User;

    @Column
    private String description;


    
    // this is the critic made from a user rating a particular film,
    // later this will modifiy the film.rating itself
}
