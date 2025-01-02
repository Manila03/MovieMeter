package com.uade.tpo.demo.controllers.images;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class AddFileRequest {
    private MultipartFile file;
    private String imdbId;
}
