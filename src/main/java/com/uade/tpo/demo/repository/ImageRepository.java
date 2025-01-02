package com.uade.tpo.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.uade.tpo.demo.entity.Image;

public interface ImageRepository extends JpaRepository<Image, Long>{
    
}
