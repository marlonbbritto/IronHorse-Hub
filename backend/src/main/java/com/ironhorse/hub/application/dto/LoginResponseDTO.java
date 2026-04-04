package com.ironhorse.hub.application.dto;

/**
 * DTO para Resposta de Login contendo o token JWT.
 */
public record LoginResponseDTO(String token, String type) {}
