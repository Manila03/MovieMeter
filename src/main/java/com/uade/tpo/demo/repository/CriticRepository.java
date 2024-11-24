package com.uade.tpo.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Critic;

@Repository
public interface CriticRepository extends JpaRepository<Critic, Long> {

    @Query("SELECT c FROM Critic c WHERE c.user.id = ?1 AND c.film.id = ?2")
    Critic findByUserIdAndFilmId(Long userId, Long filmId);
}
