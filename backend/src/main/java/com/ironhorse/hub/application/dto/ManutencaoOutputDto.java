package com.ironhorse.hub.application.dto;

import com.ironhorse.hub.domain.TipoServico;
import java.time.LocalDate;

/**
 * DTO de saída para exibição de manutenção registrada.
 */
public record ManutencaoOutputDto(
    Long id,
    Long motoId,
    String descritivo,
    Double km,
    LocalDate dataServico,
    Double custo,
    String prestador,
    TipoServico tipoServico
) {}
