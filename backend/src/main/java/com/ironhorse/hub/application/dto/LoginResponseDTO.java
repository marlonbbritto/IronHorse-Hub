package com.ironhorse.hub.application.dto;

/**
 * DTO para Resposta de Login contendo o token JWT e e-mail do bando.
 */
public record LoginResponseDTO(String token, String type, String email) {}
