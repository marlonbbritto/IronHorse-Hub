package com.ironhorse.hub.application.dto;

import com.ironhorse.hub.domain.TipoServico;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * DTO de entrada para configuração manual de alertas.
 */
public record AlertaConfigInputDto(
    @NotNull(message = "O tipo de serviço é obrigatório")
    TipoServico tipoServico,

    @NotNull(message = "A periodicidade em KM é obrigatória")
    @Positive(message = "A periodicidade deve ser maior que zero")
    Double periodicidadeKm,

    boolean ativo
) {}
