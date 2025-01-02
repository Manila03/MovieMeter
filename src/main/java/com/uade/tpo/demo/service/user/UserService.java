package com.uade.tpo.demo.service.user;

import com.uade.tpo.demo.entity.User;


public interface UserService {
    public User createUser(String name, String email, String password);
    User getUserById(Long id);
    void validateUser(String email, String name);
    User getUserByEmail(String email);
    void deleteUser(Long id);
}
