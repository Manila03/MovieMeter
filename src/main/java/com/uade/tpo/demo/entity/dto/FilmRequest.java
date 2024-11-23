package com.uade.tpo.demo.entity.dto;

import lombok.Data;

@Data
public class FilmRequest {
    private String title;
    private String category;
    private String description;
    private Integer releaseYear;
    private Integer duration;
    private Integer criticRating;
    private Integer audienceRating;
    private String posterPath;
    private Integer budget;
    private Long revenue;
}
