package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.ManutencaoInputDto;
import com.ironhorse.hub.application.dto.ManutencaoOutputDto;
import com.ironhorse.hub.application.ports.in.ManutencaoUseCases;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controlador REST para gerenciamento de manutenções.
 * Expõe endpoints para registro e consulta do histórico de serviços.
 */
@RestController
@RequestMapping("/api/v1/motos/{id}/manutencoes")
public class ManutencaoController {

    private final ManutencaoUseCases.RegistrarManutencao registrarManutencao;
    private final ManutencaoUseCases.ListarHistorico listarHistorico;

    public ManutencaoController(ManutencaoUseCases.RegistrarManutencao registrarManutencao,
                                ManutencaoUseCases.ListarHistorico listarHistorico) {
        this.registrarManutencao = registrarManutencao;
        this.listarHistorico = listarHistorico;
    }

    @PostMapping
    public ResponseEntity<ManutencaoOutputDto> registrar(@PathVariable Long id,
                                                         @RequestBody @Valid ManutencaoInputDto inputDto,
                                                         Principal principal) {
        String ownerEmail = principal.getName();
        ManutencaoOutputDto outputDto = registrarManutencao.execute(id, inputDto, ownerEmail);
        return ResponseEntity.status(HttpStatus.CREATED).body(outputDto);
    }

    @GetMapping
    public ResponseEntity<List<ManutencaoOutputDto>> listar(@PathVariable Long id,
                                                            Principal principal) {
        String ownerEmail = principal.getName();
        List<ManutencaoOutputDto> historico = listarHistorico.execute(id, ownerEmail);
        return ResponseEntity.ok(historico);
    }
}
