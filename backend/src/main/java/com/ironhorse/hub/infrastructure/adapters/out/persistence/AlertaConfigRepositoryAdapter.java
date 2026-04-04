package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import com.ironhorse.hub.domain.AlertaConfig;
import com.ironhorse.hub.domain.AlertaConfigRepository;
import com.ironhorse.hub.domain.TipoServico;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistência para Configuração de Alertas.
 * Conecta o Domínio à Infraestrutura JPA.
 */
@Component
@SuppressWarnings("null")
public class AlertaConfigRepositoryAdapter implements AlertaConfigRepository {

    private final JpaAlertaConfigRepository jpaRepository;

    public AlertaConfigRepositoryAdapter(JpaAlertaConfigRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AlertaConfig save(AlertaConfig config) {
        AlertaConfigJpaEntity entity = toEntity(config);
        AlertaConfigJpaEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<AlertaConfig> findAllByMotoId(Long motoId) {
        return jpaRepository.findAllByMotoId(motoId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<AlertaConfig> findByMotoIdAndTipoServico(Long motoId, TipoServico tipoServico) {
        return jpaRepository.findByMotoIdAndTipoServico(motoId, tipoServico)
                .map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    // Mappers
    private AlertaConfigJpaEntity toEntity(AlertaConfig domain) {
        AlertaConfigJpaEntity entity = new AlertaConfigJpaEntity();
        entity.setId(domain.getId());
        entity.setMotoId(domain.getMotoId());
        entity.setTipoServico(domain.getTipoServico());
        entity.setPeriodicidadeKm(domain.getPeriodicidadeKm());
        entity.setAtivo(domain.isAtivo());
        return entity;
    }

    private AlertaConfig toDomain(AlertaConfigJpaEntity entity) {
        return new AlertaConfig(
                entity.getId(),
                entity.getMotoId(),
                entity.getTipoServico(),
                entity.getPeriodicidadeKm(),
                entity.isAtivo()
        );
    }
}
