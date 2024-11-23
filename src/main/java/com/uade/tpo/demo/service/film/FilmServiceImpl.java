package com.uade.tpo.demo.service.film;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.exceptions.FilmDuplicateException;
import com.uade.tpo.demo.repository.FilmRepository;

import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.movies.MovieDb;


@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private TmdbService tmdbService; // Servicio de TMDB

    @Override
    public Film createFilm(String title, String category, Integer releaseYear, int audienceRating) throws FilmDuplicateException {
        List<Film> matches = filmRepository.findFilmsDuplicated(title, releaseYear, category);
        if (matches.size() > 0) {
            throw new FilmDuplicateException();
        } else {
            Movie movieJson = tmdbService.getMovieDataFromTmdb(title, releaseYear);
            String posterPath = movieJson.getPosterPath();
            Double rating = movieJson.getVoteAverage();
            String description = movieJson.getOverview();
            int id = movieJson.getId();

            System.out.println(description);
            System.out.println(posterPath);
            System.out.println(rating);
            System.out.println(id);

            try {
                // Extract specific fields
                
                

                
                MovieDb movieDbJson = tmdbService.getMovieById(id);

                int duration = movieDbJson.getRuntime();
                Integer budget = movieDbJson.getBudget();
                Long revenue = movieDbJson.getRevenue();

                // Create a new Film object with the extracted fields
                
                Film film = new Film(title, category, description, releaseYear, duration, rating, audienceRating, posterPath, budget, revenue);
                return filmRepository.save(film);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(movieJson);
                System.out.println("movieJson es null");
                return null;
            }

            
        }
    }

    @Override
    public Optional<Film> getFilmById(Long id) {
        return filmRepository.findById(id);
    }

    
    @Override
    public List<Film> getBestFilms() {
        List<Film> bestFilmsSorted = filmRepository.findBestFilms();
        return bestFilmsSorted.subList(0, Math.min(25, bestFilmsSorted.size()));
    }

    @Override
    public List<Film> getFilmsByCategory(String category) {
        List<Film> filmsByCategory = filmRepository.findFilmsByCategory(category);
        System.out.println(filmsByCategory);
        return filmsByCategory;
    }

    @Override
    public Double getFilmCriticRating(Long id) {
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getCriticRating();
    }

    @Override
    public Integer getFilmAudienceRating(Long id) {
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getAudienceRating();
    }

    @Override
    public String getFilmPosterPath(Long id) {
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getPosterPath();
    }

    @Override
    public String getFilmCategory(Long id){
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getCategory();
    }

    @Override
    public String getFilmDescription(Long id){
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getDescription();
    }

    @Override
    public String getFilmTitle(Long id){
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getTitle();
    }

    @Override
    public Integer getFilmReleaseYear(Long id){
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getReleaseYear();
    }

    @Override
    public Integer getFilmDuration(Long id){
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getDuration();
    }

    @Override
    public Integer getFilmBudget(Long id){
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getBudget();
    }

    @Override
    public Long getFilmRevenue(Long id){
        Optional<Film> film = filmRepository.findById(id);
            return film.get().getRevenue();
    }
    

    // TODO falta implementar un metodo tipo update a la audienceRating de una film,
    // cada vez que se genere una Critic nueva =)
}
