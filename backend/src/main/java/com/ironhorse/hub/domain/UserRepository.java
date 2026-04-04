package com.ironhorse.hub.domain;

import java.util.Optional;
import java.util.UUID;

/**
 * Interface do Repositório de Domínio para Usuário.
 * Define a porta de saída.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
}
