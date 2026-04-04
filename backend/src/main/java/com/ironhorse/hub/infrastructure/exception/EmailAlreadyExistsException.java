package com.ironhorse.hub.infrastructure.exception;

/**
 * Exceção lançada quando um e-mail já está cadastrado no sistema.
 */
public class EmailAlreadyExistsException extends RuntimeException {
    public EmailAlreadyExistsException(String message) {
        super(message);
    }
}
