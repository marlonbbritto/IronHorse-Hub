package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.MotoInputDto;
import com.ironhorse.hub.application.dto.MotoOutputDto;
import com.ironhorse.hub.application.ports.in.CadastrarMotoUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller REST para exposição dos recursos de Motos.
 * Localizado na camada de Infrastructure (Entrada/Input Adapter).
 */
@RestController
@RequestMapping("/api/v1/motos")
public class MotoController {

    private final CadastrarMotoUseCase cadastrarMotoUseCase;

    public MotoController(CadastrarMotoUseCase cadastrarMotoUseCase) {
        this.cadastrarMotoUseCase = cadastrarMotoUseCase;
    }

    @PostMapping
    public ResponseEntity<MotoOutputDto> cadastrar(@RequestBody @Valid MotoInputDto inputDto) {
        MotoOutputDto outputDto = cadastrarMotoUseCase.execute(inputDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(outputDto);
    }
}
