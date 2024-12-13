package com.uade.tpo.demo.service.Actor;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.Actor;
import com.uade.tpo.demo.repository.ActorRepository;

@Service
public class ActorServiceImpl implements ActorService {
    @Autowired
    private ActorRepository actorRepository;

    // @Override
    // public Actor createActor(String name, String department, String picturePath, List<Long> knownFor) {
    //     return null;
    // }
    @Override
    public Actor getOrCreateActor(String imdbId, String name, String picture){
        Optional<Actor> existingActor = actorRepository.findByImdbId(imdbId);
        if (existingActor.isPresent()) {
            return existingActor.get();
        }
        
        Actor newActor = new Actor();
        newActor.setImdbNameId(imdbId);
        newActor.setName(name);
        newActor.setPicture(picture);
        return actorRepository.save(newActor);
    }
}
