package com.uade.tpo.demo.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Category;
import com.uade.tpo.demo.exceptions.CategoryDuplicateException;

@Repository
public interface CategoryRepository extends JpaRepository <Category, Long>{
    // Arriba en la interfaz colocamos '<Category, Integer>' por que Category 
    // es la entidad a la que le hacemos consultas 
    // y Integer es el tipo de dato de la primary key de la clase Category
    
    @Query("SELECT c FROM Category c WHERE c.description = ?1")
    List<Category> findByDescription(String description);

    // List<Category> electronics = categoryRepository.findByDescription("Electronics");
    // Spring sustituye ?1 con el valor "Electronics".


    // Un ejemplo para esclarecer el asunto:

    // @Query("SELECT c FROM Category c WHERE c.description = ?1 AND c.id > ?2")
    // List<Category> findByDescriptionAndMinId(String description, Long minId);

}
