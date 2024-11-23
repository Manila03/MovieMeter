package com.uade.tpo.demo.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL) // Relación inversa con User
    private List<User> users; // Lista de usuarios asociados al rol

    // @Entity lo que hace es indicar que esta clase es una entidad de la base de datos

}
