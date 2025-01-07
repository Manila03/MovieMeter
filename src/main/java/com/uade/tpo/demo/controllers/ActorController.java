package com.uade.tpo.demo.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.uade.tpo.demo.service.IMDBDataSheet;
import com.uade.tpo.demo.service.Actor.ActorService;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("actor")
public class ActorController {
    private ActorService actorService;
    private IMDBDataSheet imdbDataSheet = new IMDBDataSheet();

    // @GetMapping("/loadActors/{filmId}")
    // public void loadActors(@PathVariable String filmId) {
    //     System.out.println(imdbDataSheet);
    //     System.out.println(filmId);
    //     imdbDataSheet.loadActors(filmId);
    // }
}
