package com.uade.tpo.demo.entity;

import org.hibernate.annotations.Columns;

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
public class User {
    public User() {
        // Constructor por defecto requerido por JPA
    }

    public User(String userName, String email, String password) {
        this.userName = userName;
        this.email = email;
        this.password = password;
    }
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column
    private String userName;
    @Column
    private String email;
    @Column
    private String password;
    
    // @Data hace que se generen los getters y setters automaticamente
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false) // Define la clave foránea en la tabla User
    private Role role; // Asociación hacia Role

    // @JoinColumn: 
    // Especifica que esta relación se mapeará con una clave foránea en la tabla User.
    
    // name = "role_id":
    // Nombra la columna en la tabla User que será usada como clave foránea hacia la tabla Role.
    
    // nullable = false:
    // Asegura que esta columna no permita valores null, es decir, cada usuario debe tener un rol asignado.
}
