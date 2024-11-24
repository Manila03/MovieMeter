package com.uade.tpo.demo.entity.dto;

import org.jetbrains.annotations.NotNull;

import lombok.Data;

@Data
public class CriticRequest {
    private String description;
    
    private int value;
    
    @NotNull
    private Long filmId;

    @NotNull
    private Long userId;
}
