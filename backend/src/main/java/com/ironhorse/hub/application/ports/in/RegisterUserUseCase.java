package com.ironhorse.hub.application.ports.in;

import com.ironhorse.hub.domain.User;

/**
 * Porta de Entrada para o Caso de Uso de Cadastro de Usuário.
 */
public interface RegisterUserUseCase {
    User register(String email, String password);
}
