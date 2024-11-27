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

@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String role;

    @OneToMany(mappedBy = "role", cascade = CascadeType.ALL, orphanRemoval = true) // Relación inversa con User
    private List<User> users = new ArrayList<>(); // Lista de usuarios asociados al rol

    // @Entity lo que hace es indicar que esta clase es una entidad de la base de datos
    public void addUser(User user) {
        users.add(user);
        user.setRole(this); // Establece la relación inversa
    }
    public void removeUser(User user) {
        users.remove(user);
        user.setRole(null);
    }
}
