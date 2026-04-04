package com.ironhorse.hub.domain;

/**
 * Interface para a geração de tokens de autenticação.
 * Define a porta de saída.
 */
public interface TokenProvider {
    AuthToken generateToken(User user);
    String getSubject(String token);
    boolean isTokenValid(String token);
}
