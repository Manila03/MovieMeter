package com.uade.tpo.demo.service.user;

import java.util.Optional;

import com.uade.tpo.demo.entity.User;


public interface UserService {
    public User createUser(String name, String email, String password);
    User getUserById(Long id);
    Void validateUser(String email, String name);
    User getUserByEmail(String email);
    // TODO implementar la comprobacion de si el mail y contraseñas son correctos al hacer un login
}
