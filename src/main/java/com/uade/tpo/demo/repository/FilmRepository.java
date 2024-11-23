package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Film;

@Repository
public interface FilmRepository extends JpaRepository<Film, Long>{
    // JpaRepository es una interfaz que nos permite hacer consultas a la base de datos
    // con las operaciones basicas de CRUD (Create, Read, Update, Delete)
    // y tambien con las operaciones de consulta personalizadas

    @Query("SELECT f FROM Film f ORDER BY (f.criticRating) DESC")
    List<Film> findBestFilms();


    // IMPORTANTE
    // no voy a hacer una tabla para cada genero, simplemente a cada pelicula le asigno un genero, el cual debe ser un genero valido
    // y luego simplemente busco entre todas las peliculas las que tienen cierto genero utilizando un query de sql

    @Query ("SELECT f FROM Film f WHERE f.category = ?1")
    List<Film> findFilmsByCategory(String category);

    @Query("SELECT f FROM Film f WHERE f.title = :title AND f.releaseYear = :releaseYear AND f.category = :category")
    List<Film> findFilmsDuplicated(@Param("title") String title, @Param("releaseYear") Integer releaseYear, @Param("category") String category);

}
