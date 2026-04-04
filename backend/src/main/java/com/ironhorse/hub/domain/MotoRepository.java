package com.ironhorse.hub.domain;

import java.util.List;
import java.util.Optional;

/**
 * Interface de porta de saída para persistência de Motos.
 * Segue os princípios da Arquitetura Hexagonal.
 */
public interface MotoRepository {
    Moto save(Moto moto);
    Optional<Moto> findById(Long id);
    List<Moto> findAll();
    List<Moto> findAllByOwnerEmail(String email);
    void deleteById(Long id);
}
