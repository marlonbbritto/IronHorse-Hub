package com.ironhorse.hub.application.ports.in;

import com.ironhorse.hub.application.dto.ManutencaoInputDto;
import com.ironhorse.hub.application.dto.ManutencaoOutputDto;
import java.util.List;

/**
 * Portas de entrada para os casos de uso de Manutenção.
 * Segue os princípios da Arquitetura Hexagonal.
 */
public interface ManutencaoUseCases {

    /**
     * Registra uma nova manutenção para uma moto específica.
     */
    interface RegistrarManutencao {
        ManutencaoOutputDto execute(Long motoId, ManutencaoInputDto input, String ownerEmail);
    }

    /**
     * Lista o histórico de manutenções de uma moto.
     */
    interface ListarHistorico {
        List<ManutencaoOutputDto> execute(Long motoId, String ownerEmail);
    }
}
