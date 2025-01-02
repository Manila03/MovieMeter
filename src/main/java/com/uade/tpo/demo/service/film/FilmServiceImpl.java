package com.uade.tpo.demo.service.film;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.uade.tpo.demo.entity.Actor;
import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.exceptions.FilmDuplicateException;
import com.uade.tpo.demo.repository.ActorRepository;
import com.uade.tpo.demo.repository.CriticRepository;
import com.uade.tpo.demo.repository.FilmRepository;
import com.uade.tpo.demo.service.IMDBDataSheet;
import com.uade.tpo.demo.service.Actor.ActorService;
import com.uade.tpo.demo.service.category.CategoryService;

import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.find.FindPerson;
import info.movito.themoviedbapi.model.movies.MovieDb;


@Service
public class FilmServiceImpl implements FilmService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private IMDBDataSheet imdbDataSheet;

    @Autowired
    private CriticRepository criticRepository;

    @Autowired
    private ActorService actorService;

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
                
                Film film = new Film(title, category, description, releaseYear, duration, criticRating,1,  posterPath, budget, revenue, voteCount);
                
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
    public Page<Film> getBestFilms(PageRequest pageRequest) {
        Page<Film> bestFilmsSorted = filmRepository.findBestFilms(pageRequest);
        return bestFilmsSorted;
        //return bestFilmsSorted.subList(0, Math.min(25, bestFilmsSorted.size()));
    }

    @Override
    public Page<Film> getFilmsByCategory(PageRequest pageRequest, String category) {
        Page<Film> filmsByCategory = filmRepository.findFilmsByCategory(pageRequest, category);
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

    /* 
    @Override
    public Film updateFilmAudienceRating(Long id){
        System.out.println(id);
        Page<Critic> allCriticsFromFilm = criticRepository.allCriticsFromFilm(id);
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
    */

    @Override
    public Film updateFilmAudienceRating(Long id) {
        System.out.println("Film ID: " + id);

        // Obtener todas las críticas de la película (con paginación)
        Page<Critic> allCriticsPage = criticRepository.allCriticsFromFilm(PageRequest.of(0, Integer.MAX_VALUE), id);
        List<Critic> allCritics = allCriticsPage.getContent(); // Extraer el contenido de la página
        int lenAll = allCritics.size();

        System.out.println("Total Critics: " + lenAll);

        // Si no hay críticas, establecer el puntaje como 0 o un valor predeterminado
        if (lenAll == 0) {
            System.out.println("No critics found");
            Film film = filmRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Film not found"));
            film.setAudienceRating(0); // Valor predeterminado
            return filmRepository.save(film);
        } else {
            // Obtener críticas positivas
            Page<Critic> positiveCriticsPage = criticRepository.positiveCriticsFromFilm(PageRequest.of(0, Integer.MAX_VALUE), id);
            List<Critic> positiveCritics = positiveCriticsPage.getContent(); // Extraer el contenido de la página
            int lenPositives = positiveCritics.size();

            System.out.println("Positive Critics: " + lenPositives);

            // Calcular el nuevo puntaje (como porcentaje)
            int newAudienceRating = Math.round(((float) lenPositives / lenAll) * 100);

            // Actualizar la película
            Film film = filmRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Film not found"));
            System.out.println("Film: " + film);

            film.setAudienceRating(newAudienceRating);
            return filmRepository.save(film);
        }
    }
    
    
//     @Override
//     public void loadActors() {
//         int totalPages = 300;
//         for (int page = 1; page < totalPages; page++){
//             System.out.println("ahora busco los resultados de la pagina: "+page);
//             try {
//                 String url = "https://api.themoviedb.org/3/person/popular?language=en-US&page=";
//                 JsonNode results = tmdbService.loadApiResults(page, url); 
//                 for (JsonNode actor : results) {
//                     String name = actor.path("name").toString();
//                     String department = actor.path("known_for_department").toString();
//                     String image = actor.path("profile_path").toString();
//                     for (JsonNode elem : actor.path("known_for")) {
//                         Long filmId = elem.path("id").longValue();
//                     } 
//                 }
//             } finally {

//                 }
//             }
//     }

    @Override
    public void loadFilms() {
        int totalPages = 1000;
        
        
        for (int page = 1; page < totalPages; page++){
            System.out.println("ahora busco los resultados de la pagina: "+page);
            try {
                // String url = "https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=";
                // List<JsonNode> apiResults = tmdbService.loadApiResults(page, url);
                // JsonNode results = apiResults.get(0);

                // if (results == null || results.isEmpty()) {
                //     System.out.println("No se encontraron resultados en la página: " + page);
                //     break;
                // }

                MovieResultsPage resultsPage = null;
                //tmdbService.loadResultsPage(page);
                try {
                    resultsPage = tmdbService.loadResultsPage(page);
                } catch (Exception e) {
                    System.out.println("Error al obtener la página: " + page);
                    continue;
                }
                if (resultsPage == null) {
                    continue;
                }

                List<Movie> results = resultsPage.getResults();

                for (Movie movie : results) {
                        // Extraer los datos de cada película
                        int categoryId = movie.getGenreIds().get(0);
                        String category = categoryService.getCategoryById(Long.valueOf(categoryId)).orElseThrow(() -> new RuntimeException("category not found")).getDescription();
                        System.out.println("Category: " + category);

                        String title = movie.getTitle();
                        System.out.println("Title: " + title);

                        String releaseDate = movie.getReleaseDate().substring(0, 4);
                        System.out.println("Release Date: " + releaseDate);

                        String overview = movie.getOverview();
                        System.out.println("Overview: " + overview);

                        String posterPath = movie.getPosterPath();
                        System.out.println("Poster Path: " + posterPath);

                        int tmdbId = movie.getId();
                        System.out.println("TMDb ID: " + tmdbId);
                        
                        MovieDb movieDetails = null;
                        try {
                            movieDetails = tmdbService.getMovieById(tmdbId);
                        } catch (Exception e) {
                            System.out.println("Error: " + e.getMessage());
                            continue;
                        }
                        if (movieDetails == null) {
                            continue;
                        }

                        String imdbId = movieDetails.getImdbID();
                        System.out.println("IMDB ID: " + imdbId);
                        
                        Integer budget = movieDetails.getBudget();
                        System.out.println("Budget: " + budget);
                        
                        Long revenue = movieDetails.getRevenue();
                        System.out.println("Revenue: " + revenue);

                        Integer duration = movieDetails.getRuntime();
                        System.out.println("Duration: " + duration);

                        List<String> filmRatingResults = imdbDataSheet.getFilmRatingFromImdb(imdbId);
                        if (filmRatingResults == null || filmRatingResults.size() < 3) {
                            System.out.println("Ratings no disponibles o incompletos para IMDB ID: " + imdbId);
                            continue;
                        }
                        System.out.println(filmRatingResults);
                        // filmRatingResults[1] == rating
                        // filmRatingResults[2] == vote count

                        Film film = new Film();
                        film.setTitle(title);
                        film.setCategory(category);
                        film.setReleaseYear(Integer.parseInt(releaseDate));
                        film.setBudget(budget);
                        film.setRevenue(revenue);
                        film.setDuration(duration);
                        film.setAudienceRating(1);
                        film.setCriticRating(Double.parseDouble(filmRatingResults.get(1)));
                        film.setVoteCount(Integer.parseInt(filmRatingResults.get(2)));
                        film.setPosterPath(posterPath);
                        film.setDescription(overview);
                        film.setImdbId(imdbId);

                        List<Film> findFilm = filmRepository.findFilmsDuplicated(imdbId);
                        System.out.println(findFilm);
                        if (findFilm.size() == 0) {

                            System.out.println("no se encontro otra pelicula con titulo, duracion y categoria: " + title + " " + duration + " " + category);
                            filmRepository.save(film);
                            List<String> filmActorsResults = imdbDataSheet.getActorsFromImdb(imdbId);
                            if (filmActorsResults == null) {
                                System.out.println("actores no disponibles para IMDB ID: " + imdbId);
                                continue;
                            }
                            System.out.println(filmActorsResults);
                            for (String actorImdbId : filmActorsResults) {
                                try {
                                    System.out.println("actualmente intentando crear al actor: " + actorImdbId);
                                    List<FindPerson> findActor = tmdbService.getFindResultsFromImdbId(actorImdbId).getPersonResults();
                                    if (findActor.size() > 0) {
                                    FindPerson actorFound = findActor.get(0);

                                    Actor actor= actorService.getOrCreateActor(actorImdbId,actorFound.getName(),actorFound.getProfilePath());
                                    
                                    film.getActors().add(actor);
                                    actor.getFilmsActed().add(film);
                                    filmRepository.save(film);
                                    } else {
                                        continue;
                                    }
                                } catch (Exception e) {
                                    System.out.println("Error al crear actor: " + actorImdbId+ ". Detalles: " + e.getMessage());
                                    continue;
                                }
                            }
                            filmRepository.save(film);
                            totalPages = Math.min(totalPages, (resultsPage.getTotalPages()));
                    }

                        // Guardar los datos en la base de datos
                        // int categoryId = movie.path("genre_ids").get(0).asInt();
                        

                        // String releaseDate = movie.path("release_date").asText().substring(0,4);
                        // System.out.println("Release Date: " + releaseDate);

                        // String title = movie.path("title").asText();
                        // System.out.println("title: " + title);

                        // System.out.println("ahora vamos a enviar el metodo de filmService para crear la pelicula");
                        // String category = categoryService.getCategoryById(Long.valueOf(categoryId)).orElseThrow(() -> new RuntimeException("category not found")).getDescription();

                        // String imagePath = movie.path("poster_path").toString();
                        // System.out.println("Image Path: " + imagePath);

                        // String description = movie.path("overview").toString();
                        // System.out.println("Description: " + description);

                        // int tmdbId = movie.path("id").asInt();
                        // System.out.println("TMDB ID: " + tmdbId);


                        // System.out.println("the film " + title + " got a vote count of: " + movie.path("vote_count").asInt());

                        //MovieDb movieDetails = tmdbService.getMovieById(tmdbId);
                    }
            }
            finally {
                System.out.println("no se pudo obtener 'results'");
            }
            
    }
}
}
