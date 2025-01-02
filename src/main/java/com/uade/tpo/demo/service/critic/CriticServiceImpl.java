package com.uade.tpo.demo.service.critic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.CriticRequest;
import com.uade.tpo.demo.repository.CriticRepository;
import com.uade.tpo.demo.repository.FilmRepository;
import com.uade.tpo.demo.repository.UserRepository;
import com.uade.tpo.demo.service.film.FilmServiceImpl;

import jakarta.persistence.EntityNotFoundException;


@Service
public class CriticServiceImpl implements CriticService {

    @Autowired
    private CriticRepository criticRepository;
    
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FilmServiceImpl filmServiceImpl;



    @Transactional
    public Critic createCritic(CriticRequest criticRequest) {
        Critic alreadyExist = criticRepository.findByUserIdAndFilmId(criticRequest.getUserId(), criticRequest.getFilmId());
        if(alreadyExist != null){
            throw new EntityNotFoundException("You already made a critic of this film!");
        } else {
            // Busca el User y el Film asociados, lanzando excepciones descriptivas si no existen
            User user = userRepository.findById(criticRequest.getUserId())
            .orElseThrow(() -> new EntityNotFoundException("User not found with userId: " + criticRequest.getUserId()));
            Film film = filmRepository.findById(criticRequest.getFilmId())
            .orElseThrow(() -> new EntityNotFoundException("Film not found with filmId: " + criticRequest.getFilmId()));

            // Valida los datos del CriticRequest
            validateCriticRequest(criticRequest);

            // Crea y configura la nueva crítica
            Critic critic = new Critic();
            critic.setFilm(film);
            critic.setUser(user);
            critic.setDescription(criticRequest.getDescription());
            critic.setValue(criticRequest.getValue());

            // Relación bidireccional: añade la crítica a las listas de User y Film
            user.getCritics().add(critic);
            film.getCritics().add(critic);

            // Guarda y retorna la crítica
            Critic newCritic = criticRepository.save(critic);

            // aca lo que hicimos fue actualizar la calificacion de la pelicula
            return newCritic;
        }

        
    }

    // Método para validar el CriticRequest
    private void validateCriticRequest(CriticRequest criticRequest) {
        Integer rating = criticRequest.getValue();
        String coment = criticRequest.getDescription();

        if (rating == null || rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        if (coment == null || coment.isBlank()) {
            throw new IllegalArgumentException("Description cannot be null or blank");
        }
    }

    public Page<Critic> getCriticsFromUser(PageRequest pageRequest, Long userId) {
        return criticRepository.criticsByUser(pageRequest, userId);
    }

    public Page<Critic> getCriticsFromFilm(PageRequest pageRequest, Long filmId) {
        return criticRepository.allCriticsFromFilm(pageRequest, filmId);
    }

    public Critic getCriticFromFilmAndUser(Long userId, Long filmId) {
        return criticRepository.findByUserIdAndFilmId(userId,filmId);
        
    }

    
    public Critic updateCritic(Long criticId, CriticRequest criticRequest) {
        Critic critic = criticRepository.findById(criticId).orElseThrow(() -> new RuntimeException("Critic not found"));
        critic.setDescription(criticRequest.getDescription());
        critic.setValue(criticRequest.getValue());
        
        criticRepository.save(critic);
        filmServiceImpl.updateFilmAudienceRating(criticRequest.getUserId());
        // aca lo que hicimos fue actualizar la calificacion de la pelicula
        return critic;
    }

    public void deleteCritic(Long criticId) {
        Critic critic = criticRepository.findById(criticId).orElseThrow(() -> new RuntimeException("Critic not found in criticServiceImpl"));
        
        Long filmId = critic.getFilm().getId();
        criticRepository.delete(critic);
        filmServiceImpl.updateFilmAudienceRating(filmId);

        // TODO cuando eliminamos una critica y al querer actualizar la calificacion de la pelicula, no la encuentra
    }

}
