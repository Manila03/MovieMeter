package com.uade.tpo.demo.controllers;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;



import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.entity.dto.FilmRequest;
import com.uade.tpo.demo.exceptions.FilmDuplicateException;
import com.uade.tpo.demo.service.IMDBDataSheet;
import com.uade.tpo.demo.service.film.FilmService;

import io.micrometer.core.ipc.http.HttpSender.Response;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("films")
public class FilmController {
    @Autowired
    private IMDBDataSheet imdbDataSheet;

    @Autowired
    private FilmService filmService;

    @PostMapping
    public ResponseEntity<Object> createFilm(@RequestBody FilmRequest filmRequest) throws FilmDuplicateException {
        Film result = filmService.createFilm(filmRequest.getTitle(), filmRequest.getCategory(), filmRequest.getReleaseYear());
        return ResponseEntity.created(URI.create("/film/" +result.getId())).body(result);
    }
    
    @GetMapping("/{filmId}")
    public ResponseEntity<Film> getFilmById(@PathVariable Long filmId) {
        Optional<Film> result = filmService.getFilmById(filmId);
        if (result.isPresent()) {
            return ResponseEntity.ok(result.get());
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping("/best")
    public ResponseEntity<Page<Film>> getBestFilms(
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size) {
        if (page == null || size == null)
            return ResponseEntity.ok(filmService.getBestFilms(PageRequest.of(0, Integer.MAX_VALUE)));
        return ResponseEntity.ok(filmService.getBestFilms(PageRequest.of(page, size)));
    }

    @GetMapping("/AllFilms")
    public ResponseEntity<Page<Film>> getAllFilms(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size) {
    if (page == null || size == null)
        return ResponseEntity.ok(filmService.getAllFilms(PageRequest.of(0, Integer.MAX_VALUE)));
    return ResponseEntity.ok(filmService.getAllFilms(PageRequest.of(page, size)));
    }

    @GetMapping("/category/name/{category}")
    // el path sera en este caso:
    // ... /film/category/{category}
    public ResponseEntity<Page<Film>> getFilmsByCategory(
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size, 
        @PathVariable String category) {
        if (page == null || size == null)
            return ResponseEntity.ok(filmService.getFilmsByCategory(PageRequest.of(0, Integer.MAX_VALUE), category));
        
        return ResponseEntity.ok(filmService.getFilmsByCategory(PageRequest.of(page, size), category));
    }

    // @GetMapping("/criticRate/{filmId}")
    // public ResponseEntity<Double>  getFilmCriticRating(@PathVariable Long filmId) {
    //     Double result = filmService.getFilmCriticRating(filmId);
    //     return ResponseEntity.ok(result);
    // }

    @GetMapping("/criticRate/{filmId}")
    public ResponseEntity<Double>  getFilmCriticRating(@PathVariable Long filmId) {
        Double result = filmService.getFilmCriticRating(filmId);
        return ResponseEntity.ok(result);
    }
    
    


    @GetMapping("/poster/{filmId}")
    public ResponseEntity<String> getFilmPosterPath(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmPosterPath(filmId));
    }

    @GetMapping("/category/{filmId}")
    public ResponseEntity<String> getFilmCategory(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmCategory(filmId));
    }

    @GetMapping("/description/{filmId}")
    public ResponseEntity<String> getFilmDescription(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmDescription(filmId));
    }

    @GetMapping("/title/{filmId}")
    public ResponseEntity<String> getFilmTitle(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmTitle(filmId));
    }

    @GetMapping("/releaseYear/{filmId}")
    public ResponseEntity<Integer> getFilmReleaseYear(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmReleaseYear(filmId));
    }

    @GetMapping("/duration/{filmId}")
    public ResponseEntity<Integer> getFilmDuration(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmDuration(filmId));
    }

    @GetMapping("/budget/{filmId}")
    public ResponseEntity<Integer> getFilmBudget(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmBudget(filmId));
    }

    @GetMapping("/revenue/{filmId}")
    public ResponseEntity<Long> getFilmRevenue(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.getFilmRevenue(filmId));
    }

    // @GetMapping("/loadFilms")
    // public void loadFilms() {
    //     filmService.loadFilms();
    // }

    @GetMapping("/loadFilms")
    public void loadDataSheet() {
        filmService.loadFilms();
    }
    
    @PutMapping("/update/{filmId}")
    public ResponseEntity<Film> updateFilmAudienceRating(@PathVariable Long filmId) {
        return ResponseEntity.ok(filmService.updateFilmAudienceRating(filmId));
    }
}