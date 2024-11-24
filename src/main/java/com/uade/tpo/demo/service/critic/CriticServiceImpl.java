package com.uade.tpo.demo.service.critic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.Film;
import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.entity.dto.CriticRequest;
import com.uade.tpo.demo.repository.CriticRepository;
import com.uade.tpo.demo.repository.FilmRepository;
import com.uade.tpo.demo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;


@Service
public class CriticServiceImpl implements CriticService {

    @Autowired
    private CriticRepository criticRepository;
    
    @Autowired
    private FilmRepository filmRepository;

    @Autowired
    private UserRepository userRepository;



    @Transactional
    public Critic createCritic(CriticRequest criticRequest) {
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
        return criticRepository.save(critic);
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




    public List<Critic> getCriticsFromUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getCritics();
    }

    public List<Critic> getCriticsFromFilm(Long filmId) {
        Film film = filmRepository.findById(filmId).orElseThrow(() -> new RuntimeException("Film not found"));
        return film.getCritics();
    }

    public Critic getCriticFromFilmAndUser(Long userId, Long filmId) {
        return criticRepository.findByUserIdAndFilmId(filmId, userId);
    }

    
    public Critic updateCritic(Long criticId, CriticRequest criticRequest) {
        Critic critic = criticRepository.findById(criticId).orElseThrow(() -> new RuntimeException("Critic not found"));
        critic.setDescription(criticRequest.getDescription());
        critic.setValue(criticRequest.getValue());
        return criticRepository.save(critic);
    }

}
