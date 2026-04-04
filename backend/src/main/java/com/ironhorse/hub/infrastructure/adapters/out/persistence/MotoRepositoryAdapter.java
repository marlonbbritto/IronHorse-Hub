package com.ironhorse.hub.infrastructure.adapters.out.persistence;

import com.ironhorse.hub.domain.Moto;
import com.ironhorse.hub.domain.MotoRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador de persistência que conecta o Domínio à infraestrutura JPA.
 * Implementa a porta de saída MotoRepository definida no Domínio.
 */
@Component
@SuppressWarnings("null")
public class MotoRepositoryAdapter implements MotoRepository {

    private final JpaMotoRepository jpaRepository;

    public MotoRepositoryAdapter(JpaMotoRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Moto save(Moto moto) {
        MotoJpaEntity entity = toEntity(moto);
        MotoJpaEntity savedEntity = jpaRepository.save(entity);
        return toDomain(savedEntity);
    }

    @Override
    public Optional<Moto> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public List<Moto> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Moto> findAllByOwnerEmail(String email) {
        return jpaRepository.findByOwnerEmail(email).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    // Métodos de mapeamento (Mappers)
    private MotoJpaEntity toEntity(Moto domain) {
        MotoJpaEntity entity = new MotoJpaEntity();
        entity.setId(domain.getId());
        entity.setModelo(domain.getModelo());
        entity.setAno(domain.getAno());
        entity.setKm(domain.getKm());
        entity.setPlaca(domain.getPlaca());
        entity.setVin(domain.getVin());
        entity.setOwnerEmail(domain.getOwnerEmail());
        return entity;
    }

    private Moto toDomain(MotoJpaEntity entity) {
        return new Moto(
            entity.getId(),
            entity.getModelo(),
            entity.getAno(),
            entity.getKm(),
            entity.getPlaca(),
            entity.getVin(),
            entity.getOwnerEmail()
        );
    }
}
