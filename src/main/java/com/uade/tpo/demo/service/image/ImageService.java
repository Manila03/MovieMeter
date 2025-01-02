package com.uade.tpo.demo.service.image;

import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.entity.Image;

public interface ImageService {
    public Image create(Image image, String imdbId);
    public Image create(Image image, Film film);
    public Image viewById(Long id);
    public String deleteImage(Long id);
}
