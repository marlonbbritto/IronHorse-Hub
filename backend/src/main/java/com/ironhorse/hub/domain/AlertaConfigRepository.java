package com.ironhorse.hub.domain;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para operações de persistência de Configuração de Alertas.
 */
public interface AlertaConfigRepository {
    AlertaConfig save(AlertaConfig config);
    List<AlertaConfig> findAllByMotoId(Long motoId);
    Optional<AlertaConfig> findByMotoIdAndTipoServico(Long motoId, TipoServico tipoServico);
    void deleteById(Long id);
}
