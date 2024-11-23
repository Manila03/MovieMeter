package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Actor {
    public Actor() {
        // Constructor por defecto requerido por JPA
    }

    public Actor(String name, int age, int oscarsWon, int filmsActed) {
        this.name = name;
        this.age = age;
        this.oscarsWon = oscarsWon;
        this.filmsActed = filmsActed;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;
    
    @Column
    private int age;

    @Column
    private int oscarsWon;

    @Column
    private int filmsActed;
    
    
}
