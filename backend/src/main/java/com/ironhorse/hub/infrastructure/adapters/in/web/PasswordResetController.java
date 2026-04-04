package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.PasswordResetConfirmDTO;
import com.ironhorse.hub.application.dto.PasswordResetRequestDTO;
import com.ironhorse.hub.application.ports.in.RequestPasswordResetUseCase;
import com.ironhorse.hub.application.ports.in.ResetPasswordUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para recuperação de senha.
 * Canal de entrada (Inbound Adapter).
 */
@RestController
@RequestMapping("/api/v1/users/password-reset")
public class PasswordResetController {

    private final RequestPasswordResetUseCase requestUseCase;
    private final ResetPasswordUseCase resetUseCase;

    public PasswordResetController(RequestPasswordResetUseCase requestUseCase, ResetPasswordUseCase resetUseCase) {
        this.requestUseCase = requestUseCase;
        this.resetUseCase = resetUseCase;
    }

    /**
     * Solicita um token de recuperação de senha.
     */
    @PostMapping("/request")
    public ResponseEntity<Void> request(@Valid @RequestBody PasswordResetRequestDTO requestDTO) {
        requestUseCase.request(requestDTO.email());
        return ResponseEntity.ok().build();
    }

    /**
     * Confirma o reset de senha com o token e nova senha.
     */
    @PostMapping("/confirm")
    public ResponseEntity<Void> confirm(@Valid @RequestBody PasswordResetConfirmDTO confirmDTO) {
        resetUseCase.reset(confirmDTO.token(), confirmDTO.newPassword());
        return ResponseEntity.ok().build();
    }
}
