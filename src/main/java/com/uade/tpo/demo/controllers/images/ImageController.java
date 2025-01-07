package com.uade.tpo.demo.controllers.images;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.service.film.FilmService;
import com.uade.tpo.demo.service.image.ImageService;

import lombok.Builder;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("images")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @Autowired
    private FilmService filmService;

    @CrossOrigin
    @GetMapping()
    public ResponseEntity<ImageResponse> displayImage(@RequestParam("id") long id) throws IOException, SQLException {
        Image image = imageService.viewById(id);
        String encodedString = Base64.getEncoder()
            .encodeToString(image.getImage().getBytes(1, (int) image.getImage().length()));
        return ResponseEntity.ok().body(ImageResponse.builder().file(encodedString).id(id).build());
    }
    
    @PostMapping()
    public String addImagePost(AddFileRequest request) throws IOException, SerialException, SQLException {
        byte[] bytes = request.getFile().getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        imageService.create(Image.builder().image(blob).build(), request.getImdbId());
        return "created";
    }
    
    @DeleteMapping("/{id}")
    public String deleteImagen(@PathVariable Long id) {
        return imageService.deleteImage(id);
    }

    // @GetMapping("/loadImage/{id}")
    // public String giveImage(@PathVariable Long id) {
    //     Film film = filmService.getFilmById(id);
    //     String cleanPosterPath = film.getPosterPath().replace("\"", "");
    //     String completePath = "https://image.tmdb.org/t/p/w500" + cleanPosterPath;
    //     System.out.println(completePath);
    //     System.out.println(film.getPosterPath());
    //     try (InputStream inputStream = new BufferedInputStream(new URL(completePath).openStream());
    //         ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

    //             byte[] buffer = new byte[1024];
    //             int bytesRead;
    //             while ((bytesRead = inputStream.read(buffer)) != -1) {
    //                 outputStream.write(buffer, 0, bytesRead);
    //             }

    //             Blob blob = new SerialBlob(outputStream.toByteArray());
    //             Image image = Image.builder().image(blob).film(film).build();
    //             imageService.create(image, film);
    //             return "created";
    //     }
    //     catch (IOException | SQLException e) {
    //     throw new RuntimeException("Error al procesar la imagen: " + e.getMessage(), e);
    //     }
    // }
    
    // Este metodo fue solo usado para cargar las imagenes a la base de datos

    // @GetMapping("/loadAllImages")
    // public void loadAllImages() {
    //     List<Film> allFilms = filmService.getAllFilms();
    //     for (Film film : allFilms) {

    //         String cleanPosterPath = film.getPosterPath().replace("\"", "");
    //         String completePath = "https://image.tmdb.org/t/p/w500" + cleanPosterPath;
    //         System.out.println(completePath);
    //         System.out.println(film.getPosterPath());
    //         try (InputStream inputStream = new BufferedInputStream(new URL(completePath).openStream());
    //             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {

    //                 byte[] buffer = new byte[1024];
    //                 int bytesRead;
    //                 while ((bytesRead = inputStream.read(buffer)) != -1) {
    //                     outputStream.write(buffer, 0, bytesRead);
    //                 }

    //                 Blob blob = new SerialBlob(outputStream.toByteArray());
    //                 Image image = Image.builder().image(blob).film(film).build();
    //                 imageService.create(image, film);
    //                 System.out.println("created");
    //         }
    //         catch (IOException | SQLException e) {
    //             throw new RuntimeException("Error al procesar la imagen: " + e.getMessage(), e);
    //         }

    //     }
    // }
    
    
}
