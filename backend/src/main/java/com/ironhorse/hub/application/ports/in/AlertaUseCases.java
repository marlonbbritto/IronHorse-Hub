package com.ironhorse.hub.application.ports.in;

import com.ironhorse.hub.application.dto.AlertaConfigInputDto;
import com.ironhorse.hub.application.dto.StatusSaudeOutputDto;
import java.util.List;

/**
 * Portas de entrada para os casos de uso de Gestão de Alertas e Saúde.
 * Segue os princípios da Arquitetura Hexagonal.
 */
public interface AlertaUseCases {

    /**
     * Configura ou atualiza uma meta de manutenção para uma moto.
     */
    interface ConfigurarAlerta {
        void configurar(Long motoId, AlertaConfigInputDto input, String ownerEmail);
    }

    /**
     * Obtém o diagnóstico de saúde da moto baseado nas metas e histórico.
     */
    interface ObterStatusSaude {
        StatusSaudeOutputDto obterStatus(Long motoId, String ownerEmail);
    }

    /**
     * Lista todas as configurações de alerta de uma moto.
     */
    interface ListarConfiguracoes {
        List<AlertaConfigInputDto> listar(Long motoId, String ownerEmail);
    }
}
