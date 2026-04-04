package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Entidade JPA para persistência de tokens de redefinição de senha.
 */
@Entity
@Table(name = "password_reset_tokens")
public class PasswordResetTokenJpaEntity {
    
    @Id
    private String token;
    private UUID userId;
    private LocalDateTime expiryDate;

    public PasswordResetTokenJpaEntity() {}

    public PasswordResetTokenJpaEntity(String token, UUID userId, LocalDateTime expiryDate) {
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
}
