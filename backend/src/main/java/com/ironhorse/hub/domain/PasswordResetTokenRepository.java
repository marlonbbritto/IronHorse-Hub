package com.ironhorse.hub.domain;

import java.util.Optional;

/**
 * Porta de Saída para o repositório de tokens de reset.
 */
public interface PasswordResetTokenRepository {
    void save(PasswordResetToken resetToken);
    Optional<PasswordResetToken> findByToken(String token);
    void deleteByUserId(java.util.UUID userId);
}
