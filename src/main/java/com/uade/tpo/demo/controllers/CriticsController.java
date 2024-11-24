package com.uade.tpo.demo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.dto.CriticRequest;
import com.uade.tpo.demo.service.critic.CriticService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@Controller
@RequestMapping("critic")
public class CriticsController {
    @Autowired
    private CriticService criticService;

    @PostMapping
    public ResponseEntity<Critic> createCritic (@RequestBody CriticRequest criticRequest) {
        return ResponseEntity.ok(criticService.createCritic(criticRequest));
    }
    @PutMapping("critic/{criticId}")
    public ResponseEntity<Critic> putMethodName(@PathVariable Long criticId, @RequestBody CriticRequest criticRequest) {
        return ResponseEntity.ok(criticService.updateCritic(criticId, criticRequest));
    }
    @GetMapping("user/{userId}")
    public ResponseEntity<List<Critic>> getCriticsFromUser (@PathVariable Long userId) {
        return ResponseEntity.ok(criticService.getCriticsFromUser(userId));
    }
    @GetMapping("film/{filmId}")
    public ResponseEntity<List<Critic>> getCriticsFromFilm (@PathVariable Long filmId) {
        return ResponseEntity.ok(criticService.getCriticsFromFilm(filmId));
    }
    @GetMapping("user/{userId}/film/{filmId}")
    public ResponseEntity<Critic> getCriticFromFilmAndUser (@PathVariable Long userId, @PathVariable Long filmId){
        return ResponseEntity.ok(criticService.getCriticFromFilmAndUser(userId, filmId));
    }
}
