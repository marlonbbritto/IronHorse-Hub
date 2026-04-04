package com.ironhorse.hub.application.dto;

import java.util.UUID;

/**
 * DTO para Resposta de Cadastro de Usuário.
 */
public record UserResponseDTO(UUID id, String email) {}
