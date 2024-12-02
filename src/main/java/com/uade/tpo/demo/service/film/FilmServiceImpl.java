package com.uade.tpo.demo.service.film;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.exceptions.FilmDuplicateException;
import com.uade.tpo.demo.repository.CriticRepository;
import com.uade.tpo.demo.repository.FilmRepository;
import com.uade.tpo.demo.service.category.CategoryService;

import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.movies.MovieDb;


@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CriticRepository criticRepository;

    @Autowired
    private TmdbService tmdbService; // Servicio de TMDB

    @Override
    public Film createFilm(String title, String category, Integer releaseYear) throws FilmDuplicateException {
        List<Film> matches = filmRepository.findFilmsDuplicated(title, releaseYear, category);
        if (matches.size() > 0) {
            throw new FilmDuplicateException();
        } else {
            Movie movieJson = tmdbService.getMovieDataFromTmdb(title, releaseYear);
            String posterPath = movieJson.getPosterPath();
            Double criticRating = movieJson.getVoteAverage();
            String description = movieJson.getOverview();
            int id = movieJson.getId();

            System.out.println(description);
            System.out.println(posterPath);
            System.out.println(id);

            

            try {
                // Extract specific fields
                MovieDb movieDbJson = tmdbService.getMovieById(id);

                int duration = movieDbJson.getRuntime();       
                Integer budget = movieDbJson.getBudget();
                Long revenue = movieDbJson.getRevenue();
                Integer voteCount = movieDbJson.getVoteCount();
                Double popularity = movieDbJson.getPopularity();

                System.out.println(popularity);

                // Create a new Film object with the extracted fields
                
                Film film = new Film(title, category, description, releaseYear, duration, criticRating,1,  posterPath, budget, revenue, voteCount, popularity);
                
                return filmRepository.save(film);
            } catch (Exception e) {
                e.printStackTrace();
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

    @Override
    public Film updateFilmAudienceRating(Long id){
        System.out.println(id);
        List<Critic> allCriticsFromFilm = criticRepository.allCriticsFromFilm(id);
        System.out.println(allCriticsFromFilm.size());
        int lenAll = allCriticsFromFilm.size();
        if (lenAll == 0) {
            System.out.println("lenAll == 0");
            Film film = filmRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Film not found in if"));
            film.setAudienceRating(0); // O un valor predeterminado
            filmRepository.save(film);
            return film;
        } else {
            List<Critic> positiveCriticsFromFilm = criticRepository.positiveCriticsFromFilm(id);
            System.out.println(positiveCriticsFromFilm);
            int lenPositives = positiveCriticsFromFilm.size();

            Integer newAudienceRating = Math.round((lenPositives / lenAll) * 100);
            Film film = filmRepository.findById(id).orElseThrow(() -> new RuntimeException("Film not found in else"));
            System.out.println(film);
            film.setAudienceRating(newAudienceRating);
            filmRepository.save(film);
            return film;
        }
    }

    @Override
    public void loadActors() {
        int totalPages = 300;
        for (int page = 1; page < totalPages; page++){
            System.out.println("ahora busco los resultados de la pagina: "+page);
            try {
                String url = "https://api.themoviedb.org/3/person/popular?language=en-US&page=";
                JsonNode results = tmdbService.loadApiResults(page, url); 
                for (JsonNode actor : results) {
                    String name = actor.path("name").toString();
                    String department = actor.path("known_for_department").toString();
                    String image = actor.path("profile_path").toString();
                    for (JsonNode elem : actor.path("known_for")) {
                        Long filmId = elem.path("id").longValue();
                    } 
                }
            } finally {

                }
            }
    }

    @Override
    public void loadFilms() {
        int totalPages = 1000;
        
        for (int page = 1; page < totalPages; page++){
            System.out.println("ahora busco los resultados de la pagina: "+page);
            try {
                String url = "https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=";
                JsonNode results = tmdbService.loadApiResults(page, url); 
                for (JsonNode movie : results) {
                        // Extraer los datos de cada película
                        int categoryId = movie.path("genre_ids").get(0).asInt();
                        System.out.println("Category: " + categoryId);

                        String releaseDate = movie.path("release_date").asText().substring(0,4);
                        System.out.println("Release Date: " + releaseDate);

                        String title = movie.path("title").asText();
                        System.out.println("Release Date: " + title);

                        System.out.println("ahora vamos a enviar el metodo de filmService para crear la pelicula");
                        String category = categoryService.getCategoryById(Long.valueOf(categoryId)).orElseThrow(() -> new RuntimeException("category not found")).getDescription();

                        try {
                            createFilm(title, category, Integer.parseInt(releaseDate));

                        } catch (FilmDuplicateException e) {
                            e.printStackTrace();
                            System.out.println("no se pudo crear la pelicula");
                        }
                    }
            }
            finally {
                System.out.println("no se pudo obtener 'results'");
            }
            
            }
}
}