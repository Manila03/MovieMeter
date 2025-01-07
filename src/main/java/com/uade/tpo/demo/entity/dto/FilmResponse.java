package com.uade.tpo.demo.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilmResponse {
    private Long id;
    
    private String title;

    private String image;

    private String category;

    private Integer releaseYear;
}
