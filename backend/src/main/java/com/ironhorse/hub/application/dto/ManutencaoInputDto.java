package com.ironhorse.hub.application.dto;

import com.ironhorse.hub.domain.TipoServico;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

/**
 * DTO de entrada para registro de nova manutenção.
 */
public record ManutencaoInputDto(
    @NotBlank(message = "O descritivo é obrigatório")
    String descritivo,

    @NotNull(message = "A quilometragem é obrigatória")
    @PositiveOrZero(message = "A quilometragem deve ser positiva")
    Double km,

    @NotNull(message = "A data do serviço é obrigatória")
    LocalDate dataServico,

    @PositiveOrZero(message = "O custo deve ser positivo")
    Double custo,

    String prestador,

    @NotNull(message = "O tipo de serviço é obrigatório")
    TipoServico tipoServico
) {}
