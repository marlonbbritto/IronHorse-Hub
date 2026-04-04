package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.MotoInputDto;
import com.ironhorse.hub.application.dto.MotoOutputDto;
import com.ironhorse.hub.application.ports.in.CadastrarMotoUseCase;
import com.ironhorse.hub.application.ports.in.ListarMotosUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/motos")
public class MotoController {

    private final CadastrarMotoUseCase cadastrarMotoUseCase;
    private final ListarMotosUseCase listarMotosUseCase;

    public MotoController(CadastrarMotoUseCase cadastrarMotoUseCase, ListarMotosUseCase listarMotosUseCase) {
        this.cadastrarMotoUseCase = cadastrarMotoUseCase;
        this.listarMotosUseCase = listarMotosUseCase;
    }

    @PostMapping
    public ResponseEntity<MotoOutputDto> cadastrar(@RequestBody @Valid MotoInputDto inputDto, Principal principal) {
        String ownerEmail = principal.getName();
        MotoOutputDto outputDto = cadastrarMotoUseCase.execute(inputDto, ownerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(outputDto);
    }

    @GetMapping
    public ResponseEntity<List<MotoOutputDto>> listar(Principal principal) {
        String ownerEmail = principal.getName();
        List<MotoOutputDto> motos = listarMotosUseCase.execute(ownerEmail);
        return ResponseEntity.ok(motos);
    }
}
