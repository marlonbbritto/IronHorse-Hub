package com.ironhorse.hub.domain;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para operações de persistência de Manutenção.
 */
public interface ManutencaoRepository {
    Manutencao save(Manutencao manutencao);
    List<Manutencao> findAllByMotoIdOrderByDataServicoDesc(Long motoId);
    Optional<Manutencao> findById(Long id);
    void deleteById(Long id);
}
