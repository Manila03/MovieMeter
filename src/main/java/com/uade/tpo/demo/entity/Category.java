package com.uade.tpo.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.GenerationType;

import lombok.Data;


@Data
@Entity
public class Category {
    
    public Category() {
        // Constructor por defecto requerido por JPA
    }
    
    public Category(String description) {
        this.description = description;
    }

    @Id
    private Long id;
    // Con la annotation @Id definimos que la variable private 'id' sera la primary key
    // con la annotation @Entity definimos que la clase Category es una entidad de la base de datos
    // con la annotation @GeneratedValue definimos que el valor de la primary key sera generado automaticamente en un orden de tipo identidad por la base de datos
    
    @Column
    private String description;
    // con la annotation @Column definimos que la variable private 'description', y el resto de varibles posteriores de la annotation @Columns seran una columna de la tabla Category
    
    
}