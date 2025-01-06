package com.uade.tpo.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    Page<Film> findBestFilms(PageRequest pageRequest);


    // IMPORTANTE
    // no voy a hacer una tabla para cada genero, simplemente a cada pelicula le asigno un genero, el cual debe ser un genero valido
    // y luego simplemente busco entre todas las peliculas las que tienen cierto genero utilizando un query de sql

    @Query ("SELECT f FROM Film f WHERE f.category = ?1")
    Page<Film> findFilmsByCategory(PageRequest pageRequest, String category);

    @Query("SELECT f FROM Film f WHERE f.title = ?1 AND f.releaseYear = ?2 AND f.category = ?3")
    List<Film> findFilmsDuplicated(String title, Integer releaseYear, String category);

    @Query("SELECT f FROM Film f WHERE f.imdbId = ?1")
    List<Film> findFilmsDuplicated(String imdbId);
    
    @Query("SELECT f FROM Film f ORDER BY f.id DESC ")
    Page<Film> findLastFilms(PageRequest pageRequest);
}
