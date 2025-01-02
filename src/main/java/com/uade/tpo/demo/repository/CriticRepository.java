package com.uade.tpo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    Page<Critic> allCriticsFromFilm(PageRequest pageRequest, Long id);

    @Query("SELECT c FROM Critic c WHERE c.film.id = ?1 AND c.value >= 3")
    Page<Critic> positiveCriticsFromFilm(PageRequest pageRequest, Long id);

    @Query("SELECT c FROM Critic c WHERE c.user.id = ?1")
    Page<Critic> criticsByUser(PageRequest pageRequest, Long userId);


}
