package com.ironhorse.hub.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO para recebimento dos dados de cadastro de uma Moto.
 * Inclui anotações de validação do Jakarta para a camada de entrada.
 */
public record MotoInputDto(
    @NotBlank(message = "Modelo é obrigatório")
    String modelo,
    
    @NotNull(message = "Ano é obrigatório")
    @Min(value = 1900, message = "Ano inválido")
    Integer ano,
    
    @NotNull(message = "KM é obrigatório")
    @Min(value = 0, message = "KM não pode ser negativo")
    Double km,
    
    String placa,
    
    String vin
) {}
