package com.uade.tpo.demo.service.critic;

import java.util.List;

import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.dto.CriticRequest;

public interface CriticService {
    public Critic createCritic(CriticRequest criticRequest);
    List<Critic> getCriticsFromUser(Long userId);
    List<Critic> getCriticsFromFilm(Long filmId);
    Critic getCriticFromFilmAndUser(Long userId, Long filmId);
    Critic updateCritic(Long criticId, CriticRequest criticRequest);
}
