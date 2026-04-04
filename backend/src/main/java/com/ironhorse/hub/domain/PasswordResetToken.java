package com.ironhorse.hub.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade de domínio para o token de recuperação de senha.
 */
public class PasswordResetToken {
    private final String token;
    private final UUID userId;
    private final LocalDateTime expiryDate;

    public PasswordResetToken(String token, UUID userId, LocalDateTime expiryDate) {
        this.token = token;
        this.userId = userId;
        this.expiryDate = expiryDate;
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public boolean isExpired() {
        return LocalDateTime.now().isAfter(expiryDate);
    }
}
