package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.LoginRequestDTO;
import com.ironhorse.hub.application.dto.LoginResponseDTO;
import com.ironhorse.hub.application.ports.in.AuthenticateUserUseCase;
import com.ironhorse.hub.domain.AuthToken;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para o processo de Login.
 */
@RestController
@RequestMapping("/api/v1/users")
public class LoginController {

    private final AuthenticateUserUseCase authenticateUserUseCase;

    public LoginController(AuthenticateUserUseCase authenticateUserUseCase) {
        this.authenticateUserUseCase = authenticateUserUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO loginRequestDTO) {
        AuthToken token = authenticateUserUseCase.authenticate(loginRequestDTO.email(), loginRequestDTO.password());
        return ResponseEntity.ok(new LoginResponseDTO(token.token(), token.type(), loginRequestDTO.email()));
    }
}
