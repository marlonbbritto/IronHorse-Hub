package com.ironhorse.hub.application.dto;

import com.ironhorse.hub.domain.TipoServico;
import java.util.List;

/**
 * Reporte consolidado da saúde da motocicleta.
 * Informa o nível de urgência de cada manutenção baseada nas metas manuais.
 */
public record StatusSaudeOutputDto(
    Long motoId,
    List<ItemSaude> itens
) {
    public record ItemSaude(
        TipoServico tipoServico,
        Double kmRestante,
        Double percentualVidaUtil, // 100% = Novo, 0% = Precisa Trocar
        String statusVisual // OK, ALERTA, CRITICO
    ) {}
}
