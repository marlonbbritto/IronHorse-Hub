package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.PasswordResetConfirmDTO;
import com.ironhorse.hub.application.dto.PasswordResetRequestDTO;
import com.ironhorse.hub.application.ports.in.RequestPasswordResetUseCase;
import com.ironhorse.hub.application.ports.in.ResetPasswordUseCase;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador REST para o fluxo de redefinição de senha.
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

    @PostMapping("/request")
    public ResponseEntity<Void> requestReset(@Valid @RequestBody PasswordResetRequestDTO requestDTO) {
        requestUseCase.request(requestDTO.email());
        return ResponseEntity.ok().build();
    }

    @PutMapping("/confirm")
    public ResponseEntity<Void> confirmReset(@Valid @RequestBody PasswordResetConfirmDTO confirmDTO) {
        resetUseCase.reset(confirmDTO.token(), confirmDTO.newPassword());
        return ResponseEntity.ok().build();
    }
}
