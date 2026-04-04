package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import com.ironhorse.hub.domain.TipoServico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

/**
 * Interface Spring Data JPA para operações na tabela de configuração de alertas.
 */
public interface JpaAlertaConfigRepository extends JpaRepository<AlertaConfigJpaEntity, Long> {
    List<AlertaConfigJpaEntity> findAllByMotoId(Long motoId);
    Optional<AlertaConfigJpaEntity> findByMotoIdAndTipoServico(Long motoId, TipoServico tipoServico);
}
