package com.ironhorse.hub.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para entrada de atualização do hodômetro.
 */
public record HodometroInputDto(
    @NotNull(message = "A quilometragem é obrigatória")
    @Min(value = 0, message = "A quilometragem não pode ser negativa")
    Double km
) {}
