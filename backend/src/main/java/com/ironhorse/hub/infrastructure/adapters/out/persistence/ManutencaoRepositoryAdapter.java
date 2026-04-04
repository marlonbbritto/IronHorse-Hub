package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import com.ironhorse.hub.domain.Manutencao;
import com.ironhorse.hub.domain.ManutencaoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistência para Manutenção.
 * Conecta o Domínio à Infraestrutura JPA.
 */
@Component
@SuppressWarnings("null")
public class ManutencaoRepositoryAdapter implements ManutencaoRepository {

    private final JpaManutencaoRepository jpaRepository;

    public ManutencaoRepositoryAdapter(JpaManutencaoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Manutencao save(Manutencao manutencao) {
        ManutencaoJpaEntity entity = toEntity(manutencao);
        ManutencaoJpaEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public List<Manutencao> findAllByMotoIdOrderByDataServicoDesc(Long motoId) {
        return jpaRepository.findAllByMotoIdOrderByDataServicoDesc(motoId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Manutencao> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    // Mappers
    private ManutencaoJpaEntity toEntity(Manutencao domain) {
        ManutencaoJpaEntity entity = new ManutencaoJpaEntity();
        entity.setId(domain.getId());
        entity.setMotoId(domain.getMotoId());
        entity.setDescritivo(domain.getDescritivo());
        entity.setKm(domain.getKm());
        entity.setDataServico(domain.getDataServico());
        entity.setCusto(domain.getCusto());
        entity.setPrestador(domain.getPrestador());
        entity.setTipoServico(domain.getTipoServico());
        return entity;
    }

    private Manutencao toDomain(ManutencaoJpaEntity entity) {
        return new Manutencao(
                entity.getId(),
                entity.getMotoId(),
                entity.getDescritivo(),
                entity.getKm(),
                entity.getDataServico(),
                entity.getCusto(),
                entity.getPrestador(),
                entity.getTipoServico()
        );
    }
}
