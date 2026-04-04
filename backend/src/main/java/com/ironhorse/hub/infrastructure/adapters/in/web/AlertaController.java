package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.ironhorse.hub.application.dto.AlertaConfigInputDto;
import com.ironhorse.hub.application.dto.StatusSaudeOutputDto;
import com.ironhorse.hub.application.ports.in.AlertaUseCases;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * Controlador REST para gestão de alertas e saúde da moto.
 */
@RestController
@RequestMapping("/api/v1/motos/{id}/alertas")
public class AlertaController {

    private final AlertaUseCases.ConfigurarAlerta configurarAlerta;
    private final AlertaUseCases.ObterStatusSaude obterStatusSaude;
    private final AlertaUseCases.ListarConfiguracoes listarConfiguracoes;

    public AlertaController(AlertaUseCases.ConfigurarAlerta configurarAlerta,
                            AlertaUseCases.ObterStatusSaude obterStatusSaude,
                            AlertaUseCases.ListarConfiguracoes listarConfiguracoes) {
        this.configurarAlerta = configurarAlerta;
        this.obterStatusSaude = obterStatusSaude;
        this.listarConfiguracoes = listarConfiguracoes;
    }

    @PutMapping
    public ResponseEntity<Void> configurar(@PathVariable Long id,
                                          @RequestBody @Valid AlertaConfigInputDto input,
                                          Principal principal) {
        configurarAlerta.configurar(id, input, principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/saude")
    public ResponseEntity<StatusSaudeOutputDto> saude(@PathVariable Long id, Principal principal) {
        StatusSaudeOutputDto saude = obterStatusSaude.obterStatus(id, principal.getName());
        return ResponseEntity.ok(saude);
    }

    @GetMapping("/config")
    public ResponseEntity<List<AlertaConfigInputDto>> listarConfig(@PathVariable Long id, Principal principal) {
        List<AlertaConfigInputDto> configs = listarConfiguracoes.listar(id, principal.getName());
        return ResponseEntity.ok(configs);
    }
}
