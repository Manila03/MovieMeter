package com.uade.tpo.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendMail(String toEmail, String subject, String body) {
        
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("theMovieMeterOfficial@gmail.com"); // correo desde el cual se envía
        message.setTo(toEmail); // correo del destinatario
        message.setSubject(subject); // asunto del correo
        message.setText(body); // cuerpo del correo

        javaMailSender.send(message);
        System.out.println("Correo enviado exitosamente.");
    }
}
