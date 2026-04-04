package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

/**
 * Interface Spring Data JPA para operações na tabela de manutenções.
 */
public interface JpaManutencaoRepository extends JpaRepository<ManutencaoJpaEntity, Long> {
    List<ManutencaoJpaEntity> findAllByMotoIdOrderByDataServicoDesc(Long motoId);
}
