package com.uade.tpo.demo.service.image;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.entity.Image;
import com.uade.tpo.demo.repository.FilmRepository;
import com.uade.tpo.demo.repository.ImageRepository;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image create(Image image, String imdbId){
        Film film = filmRepository.findFilmsDuplicated(imdbId).get(0);
        image.setFilm(film);
        film.setImage(image);
        return imageRepository.save(image);
    }

    @Override
    public Image create(Image image, Film film){
        image.setFilm(film);
        film.setImage(image);
        return imageRepository.save(image);
    }

    @Override
    public Image viewById(Long id) {
        return imageRepository.findById(id).get();
    }

    @Override
    public String deleteImage(Long id){
        Optional<Image> image = imageRepository.findById(id);
        if (image.isPresent()) {
            imageRepository.deleteById(id);
            return("imagen eliminada correctamente");
        } else {
            return("no se ha podido encontrar la imagen");
        }
    }
}
