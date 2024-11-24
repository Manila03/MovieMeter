package com.uade.tpo.demo.service.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.uade.tpo.demo.entity.User;
import com.uade.tpo.demo.repository.UserRepository;
import com.uade.tpo.demo.service.MailService;

import jakarta.persistence.EntityNotFoundException;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MailService mailService;

    public User createUser(String name, String email, String password) {
        User user = new User(name, email, password);
        return userRepository.save(user);
    }

    public User getUserById(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
        return user;
    }

    public Void validateUser(String email, String name) {
        mailService.sendMail(email, "Bienvenido a Movie-Meter, " + name, "porfavor valida tu cuenta de mail haciendo click en 'verificar'.");
        return null;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found with email: " + email));
    }

    // TODO implementar la comprobacion de si el mail y contraseñas son correctos al hacer un login

    // como valido un inicio de sesion?
    // para eso voy a hacer un GET el cual me traiga mail y contraseña para comrpobar si son iguales, si son iguales, me devuelve un token de sesion, si no, me devuelve un error
    // tanto a la hora de registrar una cuenta como en el inicio de sesion desde el front end (creo) deberia de comprobar que el usuario no meta caracteres que me puedan extraer informacion del SQL
    // o que me puedan hacer un SQL injection, para eso deberia de hacer un try catch y si se produce un error, devolver un error ???????


    
}
