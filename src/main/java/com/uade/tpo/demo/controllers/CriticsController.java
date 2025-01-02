package com.uade.tpo.demo.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.dto.CriticRequest;
import com.uade.tpo.demo.service.critic.CriticService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("critic")
public class CriticsController {
    @Autowired
    private CriticService criticService;

    @PostMapping
    public ResponseEntity<Critic> createCritic (@RequestBody CriticRequest criticRequest) {
        return ResponseEntity.ok(criticService.createCritic(criticRequest));
    }
    
    @PutMapping("/{criticId}")
    public ResponseEntity<Critic> updateCritic(@PathVariable Long criticId, @RequestBody CriticRequest criticRequest) {
        return ResponseEntity.ok(criticService.updateCritic(criticId, criticRequest));
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<Page<Critic>> getCriticsFromUser (
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size, 
        @PathVariable Long userId) {
        if (page == null || size == null)
            return ResponseEntity.ok(criticService.getCriticsFromUser(PageRequest.of(0, Integer.MAX_VALUE), userId));
        return ResponseEntity.ok(criticService.getCriticsFromUser(PageRequest.of(page, size), userId));
    }

    @GetMapping("film/{filmId}")
    public ResponseEntity<Page<Critic>> getCriticsFromFilm (
        @RequestParam(required = false) Integer page,
        @RequestParam(required = false) Integer size,
        @PathVariable Long filmId) {
        if (page == null || size == null)
            return ResponseEntity.ok(criticService.getCriticsFromFilm(PageRequest.of(0, Integer.MAX_VALUE), filmId));
        return ResponseEntity.ok(criticService.getCriticsFromFilm(PageRequest.of(page, size), filmId));
    }

    @DeleteMapping("/del/{criticId}")
    public void deleteCritic(@PathVariable Long criticId){
        criticService.deleteCritic(criticId);
    }

    @GetMapping("user/{userId}/film/{filmId}")
    public ResponseEntity<Critic> getCriticFromFilmAndUser (@PathVariable Long userId, @PathVariable Long filmId){
        return ResponseEntity.ok(criticService.getCriticFromFilmAndUser(userId, filmId));
    }

    // getCriticFromFilmAndUser entraba en bucle infinito al hacer la peticion http
    // este error se debia a que al ser una relacion de tipo: ManyToMany
    // Los errores de ciclo no son exclusivos de relaciones @ManyToMany, pero estas son susceptibles si son bidireccionales. 
    
    
    // Aunque no hayas definido métodos toString() explícitamente, el error puede deberse a que estás utilizando Lombok (a través de la anotación @Data), 
    // que automáticamente genera un método toString() para las clases. Este método generado incluye todas las propiedades del objeto, 
    // incluidas las relaciones bidireccionales como Film.
    // critics y Critic.film. Esto lleva al mismo problema de ciclo infinito.


    //Cuando llamas a Critic.toString():

    //Critic.toString() intenta imprimir el objeto film relacionado.
    //Esto activa Film.toString().
    //Cuando Film.toString() es ejecutado:

    //Film.toString() intenta imprimir la lista critics.
    //Para imprimir la lista critics, llama a Critic.toString() para cada Critic en la lista.
    //Ciclo infinito:

    //Cada Critic en la lista vuelve a llamar a Critic.toString(), que nuevamente llama a Film.toString().
    //Este ciclo continúa hasta que el programa agota la pila, lo que produce el StackOverflowErro
    
    
    // Siempre que haya referencias cruzadas entre entidades, el riesgo existe, y debes manejarlas con las estrategias adecuadas según el contexto (JSON, logs, etc.).
}
