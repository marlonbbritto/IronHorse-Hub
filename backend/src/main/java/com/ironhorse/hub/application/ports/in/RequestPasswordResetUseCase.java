package com.ironhorse.hub.application.ports.in;

/**
 * Porta de Entrada para solicitação de recuperação de senha.
 */
public interface RequestPasswordResetUseCase {
    void request(String email);
}
