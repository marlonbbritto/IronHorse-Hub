package com.ironhorse.hub.infrastructure.adapters.out.notification;

import com.ironhorse.hub.domain.EmailSender;
import org.springframework.stereotype.Component;

/**
 * Adaptador que simula o envio de e-mails via log no console.
 */
@Component
public class MockEmailSenderAdapter implements EmailSender {

    @Override
    public void sendPasswordResetEmail(String to, String token) {
        System.out.println("================================");
        System.out.println("Enviando e-mail de reset de senha para: " + to);
        System.out.println("Token: " + token);
        System.out.println("Link: http://localhost:8080/api/v1/users/password-reset/confirm?token=" + token);
        System.out.println("================================");
    }
}
