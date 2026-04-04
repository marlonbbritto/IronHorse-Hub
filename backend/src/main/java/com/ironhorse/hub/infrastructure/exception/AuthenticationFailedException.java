package com.ironhorse.hub.infrastructure.exception;

/**
 * Exceção lançada quando a autenticação falha (credenciais incorretas).
 */
public class AuthenticationFailedException extends RuntimeException {
    public AuthenticationFailedException(String message) {
        super(message);
    }
}
