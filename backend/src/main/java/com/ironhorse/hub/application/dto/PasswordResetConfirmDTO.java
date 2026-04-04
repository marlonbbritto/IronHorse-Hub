package com.ironhorse.hub.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para confirmação de reset de senha.
 */
public record PasswordResetConfirmDTO(
    @NotBlank(message = "O token é obrigatório")
    String token,

    @NotBlank(message = "A nova senha é obrigatória")
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    String newPassword
) {}
