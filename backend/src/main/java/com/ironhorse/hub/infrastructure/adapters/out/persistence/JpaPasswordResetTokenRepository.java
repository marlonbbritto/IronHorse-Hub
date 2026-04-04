package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

/**
 * Repositório Spring Data JPA para PasswordResetTokenJpaEntity.
 */
public interface JpaPasswordResetTokenRepository extends JpaRepository<PasswordResetTokenJpaEntity, String> {
    void deleteByUserId(UUID userId);
}
