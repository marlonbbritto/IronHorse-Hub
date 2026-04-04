package com.ironhorse.hub.application.ports.in;

import com.ironhorse.hub.domain.AuthToken;

/**
 * Porta de Entrada para o Caso de Uso de Autenticação.
 */
public interface AuthenticateUserUseCase {
    AuthToken authenticate(String email, String password);
}
