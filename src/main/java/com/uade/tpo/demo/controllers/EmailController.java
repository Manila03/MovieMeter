package com.uade.tpo.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.uade.tpo.demo.entity.dto.MailRequest;
import com.uade.tpo.demo.service.MailService;

@RestController
@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true", allowedHeaders = "*")
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private MailService mailService;

    @PostMapping("/send")
    public String sendEmail(@RequestBody MailRequest mailRequest) {
        mailService.sendMail(mailRequest.getTo(), mailRequest.getSubject(), mailRequest.getBody());
        return "Correo enviado exitosamente.";
    }
}
