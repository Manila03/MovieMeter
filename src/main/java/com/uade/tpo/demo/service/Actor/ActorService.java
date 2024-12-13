package com.uade.tpo.demo.service.Actor;

import java.util.List;
import java.util.Optional;

import com.uade.tpo.demo.service.Actor.ActorServiceImpl;

import com.uade.tpo.demo.entity.Actor;

public interface ActorService {

    // public Actor createActor(String name, String department, String picturePath, List<Long> knownFor);
    public Actor getOrCreateActor(String imdbId, String name, String picture);
}
