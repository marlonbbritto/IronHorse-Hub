package com.ironhorse.hub.domain;

/**
 * Porta de Saída para o envio de e-mails.
 */
public interface EmailSender {
    void sendPasswordResetEmail(String to, String token);
}
