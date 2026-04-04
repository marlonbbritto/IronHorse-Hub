package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.UserRequestDTO;
import com.ironhorse.hub.application.dto.UserResponseDTO;
import com.ironhorse.hub.application.ports.in.RegisterUserUseCase;
import com.ironhorse.hub.domain.User;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para operações de Usuário.
 * Adaptador de entrada (Inbound Adapter).
 */
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final RegisterUserUseCase registerUserUseCase;

    public UserController(RegisterUserUseCase registerUserUseCase) {
        this.registerUserUseCase = registerUserUseCase;
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO requestDTO) {
        User user = registerUserUseCase.register(requestDTO.email(), requestDTO.password());
        UserResponseDTO responseDTO = new UserResponseDTO(user.getId(), user.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
