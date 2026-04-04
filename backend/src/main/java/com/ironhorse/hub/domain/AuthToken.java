package com.ironhorse.hub.domain;

/**
 * Value Object que representa um Token de Autenticação.
 */
public record AuthToken(String token, String type) {
    public AuthToken(String token) {
        this(token, "Bearer");
    }
}
