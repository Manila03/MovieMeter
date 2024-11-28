package com.uade.tpo.demo.service.film;

import info.movito.themoviedbapi.TmdbApi;
import info.movito.themoviedbapi.TmdbDiscover;
import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.core.MovieResultsPage;
import info.movito.themoviedbapi.model.movies.MovieDb;
import info.movito.themoviedbapi.tools.builders.discover.DiscoverMovieParamBuilder;
import info.movito.themoviedbapi.tools.sortby.DiscoverMovieSortBy;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Film;



@Service
public class TmdbService {

    private final String apiKey;
    private final TmdbApi tmdbApi;
    // private final String TMDB_API_URL = "https://api.themoviedb.org/3";

    public TmdbService() {
        Dotenv dotenv = Dotenv.load();
        this.apiKey = dotenv.get("API_KEY");
        this.tmdbApi = new TmdbApi(apiKey);
    }

    public Movie getMovieDataFromTmdb(String title, Integer releaseYear) {
        try {
            MovieResultsPage preResults = tmdbApi.getSearch().searchMovie(
                title, true, null, null, 1, null, releaseYear.toString()
            );
    
            List<Movie> results = preResults.getResults();
    
            if (results != null && !results.isEmpty()) {
                Movie mostPopularMovie = results.stream()
                    .max(Comparator.comparingDouble(Movie::getPopularity))
                    .orElse(null);
    
                return mostPopularMovie;
            } else {
                System.err.println("No movies found for the given title and release year.");
                return null;
            }
        } catch (Exception e) {
            System.err.println("Error when searching for movie by title: " + e.getMessage());
            return null;
        }
    }

    public MovieDb getMovieById(int id) {
        try {
            MovieDb movieDetails = tmdbApi.getMovies().getDetails(id, null);
            return movieDetails;
        } catch (Exception e) {
            System.err.println("Error when searching for movie by id: " + e.getMessage());
            return null;
        }

    }


    
    public MovieResultsPage discoverFilmsInPage(int page) {
        
        
        DiscoverMovieParamBuilder builder = new DiscoverMovieParamBuilder();
        builder.sortBy(DiscoverMovieSortBy.VOTE_AVERAGE_DESC);
        builder.voteCountGte(1000);
        builder.page(page);
        try {
            MovieResultsPage filmsPage = tmdbApi.getDiscover().getMovie(builder);
            return filmsPage;
            // System.out.println(filmsPage.getResults());
            }
        catch (Exception e) {
            System.err.println("Error when searching for filmPage: " + e.getMessage());
            return null;
        }
        


    }

    }

    

    // public String getFilmPosterFromTmdb(String movieJson) {
    //     try {
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         JsonNode movieNode = objectMapper.readTree(movieJson);

    //         // Extract specific fields
            
    //         String posterPath = movieNode.get("poster_path").asText();
    //         return posterPath;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }

    // public Double getRatingFromTmdb(String movieJson) {
    //     try {
    //         ObjectMapper objectMapper = new ObjectMapper();
    //         JsonNode movieNode = objectMapper.readTree(movieJson);

    //         // Extract specific fields
            
    //         Double rating = movieNode.get("vote_average").asDouble();
    //         return rating;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }



// {
        //     "page": 1,
        //     "results": [
        //       {
        //         "adult": false,
        //         "backdrop_path": "/xJHokMbljvjADYdit5fK5VQsXEG.jpg",
        //         "genre_ids": [
        //           12,
        //           18,
        //           878
        //         ],
        //         "id": 157336,
        //         "original_language": "en",
        //         "original_title": "Interstellar",
        //         "overview": "The adventures of a group of explorers who make use of a newly discovered wormhole to surpass the limitations on human space travel and conquer the vast distances involved in an interstellar voyage.",
        //         "popularity": 228.27,
        //         "poster_path": "/gEU2QniE6E77NI6lCU6MxlNBvIx.jpg",
        //         "release_date": "2014-11-05",
        //         "title": "Interstellar",
        //         "video": false,
        //         "vote_average": 8.442,
        //         "vote_count": 35625
        //       },
        //       {
        //         "adult": false,
        //         "backdrop_path": "/8C32MMrMYT5gON7nXgEGeM4puH4.jpg",
        //         "genre_ids": [
        //           99
        //         ],
        //         "id": 336592,
        //         "original_language": "en",
        //         "original_title": "The Science of Interstellar",
        //         "overview": "Matthew McConaughey narrates a fascinating look at Christopher Nolan's sci-fi film, 'Interstellar', including scientific foundations, the work of consulting Scientist Kip Thorne, basic film themes, the science behind the search for planets capable of hosting life, space-time and the theory of relativity, the science of wormholes and black holes, crafting the film's visuals based on real scientific observation, the birth of the universe, the Dust Bowl and the evolution of dust as a toxin, the likelihood of future dust storms, the prospects of escaping a dying or doomed planet and the possibilities of colonizing Mars.",
        //         "popularity": 14.673,
        //         "poster_path": "/pUvbuOTHKQWrLuGY1x2pHd1evPL.jpg",
        //         "release_date": "2015-03-31",
        //         "title": "The Science of Interstellar",
        //         "video": false,
        //         "vote_average": 6.7,
        //         "vote_count": 30
        //       }
        //   ],
        //     "total_pages": 1,
        //     "total_results": 2
        //   }