package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import com.ironhorse.hub.domain.PasswordResetToken;
import com.ironhorse.hub.domain.PasswordResetTokenRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Adaptador para a persistência de tokens de reset de senha.
 */
@Component
public class PasswordResetTokenRepositoryAdapter implements PasswordResetTokenRepository {

    private final JpaPasswordResetTokenRepository jpaRepository;

    public PasswordResetTokenRepositoryAdapter(JpaPasswordResetTokenRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void save(PasswordResetToken resetToken) {
        PasswordResetTokenJpaEntity entity = new PasswordResetTokenJpaEntity(
            resetToken.getToken(),
            resetToken.getUserId(),
            resetToken.getExpiryDate()
        );
        jpaRepository.save(entity);
    }

    @Override
    public Optional<PasswordResetToken> findByToken(String token) {
        return jpaRepository.findById(java.util.Objects.requireNonNull(token))
            .map(entity -> new PasswordResetToken(entity.getToken(), entity.getUserId(), entity.getExpiryDate()));
    }

    @Override
    public void deleteByUserId(UUID userId) {
        jpaRepository.deleteByUserId(userId);
    }
}
