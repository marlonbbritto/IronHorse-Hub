package com.ironhorse.hub.application.ports.in;

/**
 * Porta de Entrada para confirmação da nova senha via token.
 */
public interface ResetPasswordUseCase {
    void reset(String token, String newPassword);
}
