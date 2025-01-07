package com.uade.tpo.demo.service.film;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.entity.dto.FilmResponse;
import com.uade.tpo.demo.exceptions.FilmDuplicateException;

public interface FilmService{
    public Film createFilm(String title, String category, Integer releaseYear) throws FilmDuplicateException;

    // public Film createFilmFromData(String title, String category, Integer releaseYear)
    
    FilmResponse getFilmById(Long id);
    
    Page<FilmResponse> getBestFilms(PageRequest pageRequest);

    Page<FilmResponse> getAllFilms(PageRequest pageRequest);
    
    Page<FilmResponse> getFilmsByCategory(PageRequest pageRequest, String category);
    
    // Double getFilmCriticRating(Long id);
    
    // String getFilmPosterPath(Long id);

    // String getFilmCategory(Long id);

    // String getFilmDescription(Long id);

    // String getFilmTitle(Long id);

    // Integer getFilmReleaseYear(Long id);

    // Integer getFilmDuration(Long id);

    // Integer getFilmBudget(Long id);

    // Long getFilmRevenue(Long id);

    Integer getFilmAudienceRating(Long id);
    
    Film updateFilmAudienceRating(Long id);

    

    // void loadActors();
    
    void loadFilms();

    Page<FilmResponse> getLastFilms(PageRequest pageRequest);
    
}
