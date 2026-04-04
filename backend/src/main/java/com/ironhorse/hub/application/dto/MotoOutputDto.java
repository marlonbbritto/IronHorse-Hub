package com.ironhorse.hub.application.dto;

/**
 * DTO para retorno dos dados da Moto cadastrada.
 */
public record MotoOutputDto(
    Long id,
    String modelo,
    Integer ano,
    Double km,
    String placa,
    String vin,
    String ownerEmail,
    java.time.LocalDate dataUltimaAtualizacao
) {}
