package com.uade.tpo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Critic;
import com.uade.tpo.demo.entity.Film;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Long> {

    @Query("SELECT c FROM Critic c WHERE c.user.id = ?1 AND c.film.id = ?2")
    Critic findByUserIdAndFilmId(Long userId, Long filmId);

    @Query("SELECT c FROM Critic c WHERE c.film.id = ?1")
    List<Critic> allCriticsFromFilm(Long id);

    @Query("SELECT c FROM Critic c WHERE c.film.id = ?1 AND c.value >= 3")
    List<Critic> positiveCriticsFromFilm(Long id);
}
