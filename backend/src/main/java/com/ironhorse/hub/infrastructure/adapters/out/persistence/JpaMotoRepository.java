package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface Spring Data JPA para acesso direto à infraestrutura.
 */
@Repository
public interface JpaMotoRepository extends JpaRepository<MotoJpaEntity, Long> {
}
