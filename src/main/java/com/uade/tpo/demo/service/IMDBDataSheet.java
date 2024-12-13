package com.uade.tpo.demo.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Actor;
import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.service.film.TmdbService;
import com.uade.tpo.demo.repository.FilmRepository;

import info.movito.themoviedbapi.model.core.Movie;
import info.movito.themoviedbapi.model.find.FindPerson;
import info.movito.themoviedbapi.model.find.FindTvSeries;
import info.movito.themoviedbapi.model.movies.MovieDb;

@Service
public class IMDBDataSheet {
    private TmdbService tmdbService;
    private FilmRepository filmRepository;
public List<String> getFilmRatingFromImdb(String imdbId){
        String filePathRatings = "/home/usuario/Escritorio/IMDB DataSheets/title.ratings.tsv";
        try (BufferedReader brRatings = new BufferedReader(new FileReader(filePathRatings))) {
            String line;
            boolean isHeader = true;

            while ((line = brRatings.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }
    
                String[] columns = line.split("\t");
    
                
                if (columns.length >= 3) {
                    String id = columns[0];
                    String rating = columns[1];
                    String voteCount = columns[2];
    
                    if (id.equals(imdbId)) {
                        System.out.println("film encontrado: " + id + ", " + rating + ", " + voteCount);
                        return Arrays.asList(id, rating, voteCount);
                        
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }



public List<String> getActorsFromImdb(String imdbId) {
    String filePath = "/home/usuario/Escritorio/IMDB DataSheets/title.principals.tsv"; // Ruta al archivo cargado
    
    List<String> actorsInFilm = new ArrayList<>();
    
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
        String line;
        boolean isHeader = true;

        int last = 0;
        

        while ((line = br.readLine()) != null) {
            if (isHeader) {
                isHeader = false;
                continue;
            }

            String[] columns = line.split("\t");

            
            if (columns.length >= 6) {
                String id = columns[0];
                int ordering;

                try {
                    ordering = Integer.parseInt(columns[1]);
                } catch (NumberFormatException e) {
                    System.err.println("Error al convertir ordering: " + columns[1]);
                    continue;
                }

                String nameId = columns[2];
                String category = columns[3];

                if (id.equals(imdbId)) {
                    
                    if (category.equals("actor") || category.equals("actress") || category.equals("self")) {
                        System.out.println("Actor encontrado: " + id + ", " + ordering + ", " + nameId + ", " + category);
                        actorsInFilm.add(nameId);
                    } else {
                        return actorsInFilm;
                    }
                    
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return actorsInFilm;
}
//     public void loadFilms() {
//     String filePathBasics = "/home/usuario/Escritorio/IMDB DataSheets/title.basics.tsv";
//     String filePathRatings = "/home/usuario/Escritorio/IMDB DataSheets/title.ratings.tsv";
//     String filePathPrincipals = "/home/usuario/Escritorio/IMDB DataSheets/title.principals.tsv";

//     try (
//         BufferedReader brBasics = new BufferedReader(new FileReader(filePathBasics));
//         BufferedReader brRatings = new BufferedReader(new FileReader(filePathRatings));
//         BufferedReader brPrincipals = new BufferedReader(new FileReader(filePathPrincipals));
//     ) {
//         String lineBasics;
//         boolean isHeaderBasics = true;

//         // Cargar ratings en memoria para acceso rápido
//         HashMap<String, String[]> ratingsMap = new HashMap<>();
//         HashMap<String, List<String>> principalsMap = new HashMap<>();
//         String lineRatings;
//         String linePrincipals;
//         boolean isHeaderRatings = true;
//         boolean isHeaderPrincipals = true;

//         while ((lineRatings = brRatings.readLine()) != null) {
//             if (isHeaderRatings) {
//                 isHeaderRatings = false; 
//                 continue;
//             }
//             String[] columnsRatings = lineRatings.split("\t");
//             if (columnsRatings.length >= 3) {
//                 ratingsMap.put(columnsRatings[0], new String[]{columnsRatings[1], columnsRatings[2]});
//             }
//         }

//         while ((linePrincipals = brPrincipals.readLine()) != null) {
//             if (isHeaderPrincipals) {
//                 isHeaderPrincipals = false;
//                 continue;
//             }
//             String[] columnsPrincipals = linePrincipals.split("\t");
//             if (columnsPrincipals.length >= 6) {
//                 String imdbId = columnsPrincipals[0];
//                 String nameId = columnsPrincipals[2];
//                 String category = columnsPrincipals[3];

//                 // Filtrar solo actores y actrices
//                 if (category.equals("actor") || category.equals("actress")) {
//                     List<String> actor = principalsMap.computeIfAbsent(imdbId, k -> new ArrayList<>());
//                     actor.add(nameId);
//                 }
//             }
//         }



        
//         while ((lineBasics = brBasics.readLine()) != null) {
//             if (isHeaderBasics) {
//                 isHeaderBasics = false; 
//                 continue;
//             }

//             String[] columnsBasics = lineBasics.split("\t");

//             if (columnsBasics.length >= 9 && (columnsBasics[1].equals("movie") || columnsBasics[1].equals("tvMiniSeries") || columnsBasics[1].equals("tvSeries")) && columnsBasics[4].equals("0")) {

//                 String imdbId = columnsBasics[0];
//                 String type = columnsBasics[1];
//                 String title = columnsBasics[2];
//                 Integer releaseYear = Integer.parseInt(columnsBasics[5]);
//                 Integer duration = columnsBasics[7].equals("\\N") ? null : Integer.parseInt(columnsBasics[7]);
//                 String category = columnsBasics[8];

//                 // Obtener ratings de la película
//                 String[] ratings = ratingsMap.get(imdbId);
//                 if (ratings != null) {
//                     Double imdbRating = Double.parseDouble(ratings[0]);
//                     Integer voteCount = Integer.parseInt(ratings[1]);

//                     Film film = new Film();
//                     film.setTitle(title);
//                     film.setType(type);
//                     film.setImdbId(imdbId);
//                     film.setVoteCount(voteCount);
//                     film.setCriticRating(imdbRating);
//                     film.setReleaseYear(releaseYear);
//                     film.setDuration(duration);
//                     film.setCategory(category);
                    
//                     if (columnsBasics[1].equals("movie")) {
//                         Movie tmdbResponse = tmdbService.getMovieFromImdbId(imdbId).getMovieResults().get(0);
//                         String posterPath = tmdbResponse.getPosterPath();
//                         String overview = tmdbResponse.getOverview();
//                         film.setPosterPath(posterPath);
//                         film.setDescription(overview);
//                     } else if (columnsBasics[1].equals("tvSeries") || columnsBasics[1].equals("tvMiniSeries")) {
//                         FindTvSeries tmdbResponse = tmdbService.getMovieFromImdbId(imdbId).getTvSeriesResults().get(0);
//                         String posterPath = tmdbResponse.getPosterPath();
//                         String overview = tmdbResponse.getOverview();
//                         film.setPosterPath(posterPath);
//                         film.setDescription(overview);
//                     }
                    
                    

//                     // Crear y guardar la película
                    
                    
//                     List<String> actors = principalsMap.get(imdbId);
//                     for(String actorId: actors){
//                         // TODO: comprobar la existencia del actor
//                         FindPerson findActor = tmdbService.getMovieFromImdbId(actorId).getPersonResults().get(0);
//                         Actor newActor = new Actor();
//                         newActor.setName(findActor.getName());
//                         newActor.setPicture(findActor.getProfilePath());
//                         newActor.setImdbNameId(imdbId);
//                         film.getActors().add(newActor);
//                         newActor.getFilmsActed().add(film);
//                     }

//                     // Guardar la película en la base de datos
//                     //Film newFilm = filmRespository.save(film);
//                 }
//             }
//         }

//     } catch (IOException e) {
//         e.printStackTrace();
//     }
// }

    
    
}

