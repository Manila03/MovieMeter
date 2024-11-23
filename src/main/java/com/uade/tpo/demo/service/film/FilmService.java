package com.uade.tpo.demo.service.film;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.exceptions.FilmDuplicateException;

public interface FilmService{
    public Film createFilm(String title, String category, Integer releaseYear, int audienceRating) throws FilmDuplicateException;
    
    Optional<Film> getFilmById(Long id);
    
    List<Film> getBestFilms();
    
    List<Film> getFilmsByCategory(String category);
    
    Double getFilmCriticRating(Long id);
    
    Integer getFilmAudienceRating(Long id);


    
    String getFilmPosterPath(Long id);

    String getFilmCategory(Long id);

    String getFilmDescription(Long id);

    String getFilmTitle(Long id);

    Integer getFilmReleaseYear(Long id);

    Integer getFilmDuration(Long id);

    Integer getFilmBudget(Long id);

    Long getFilmRevenue(Long id);

    

    // TODO falta implementar un metodo tipo update a la audienceRating de una film,
    // TODO falta el getFilmBudget y el getFilmRevenue
}
