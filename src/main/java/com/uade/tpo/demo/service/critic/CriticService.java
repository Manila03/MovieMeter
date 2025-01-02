package com.uade.tpo.demo.service.critic;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.dto.CriticRequest;

public interface CriticService {
    public Critic createCritic(CriticRequest criticRequest);
    Page<Critic> getCriticsFromUser(PageRequest pageRequest, Long userId);
    Page<Critic> getCriticsFromFilm(PageRequest pageRequest, Long filmId);
    Critic getCriticFromFilmAndUser(Long userId, Long filmId);
    Critic updateCritic(Long criticId, CriticRequest criticRequest);
    void deleteCritic(Long criticId);
}
