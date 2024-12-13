package com.uade.tpo.demo.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.uade.tpo.demo.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
    @Query("SELECT a FROM Actor a WHERE a.imdbNameId = ?1")
    Optional<Actor> findByImdbId(String imdbNameId);
}
