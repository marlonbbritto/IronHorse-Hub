package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.dto.AlertaConfigInputDto;
import com.ironhorse.hub.application.dto.StatusSaudeOutputDto;
import com.ironhorse.hub.application.ports.in.AlertaUseCases;
import com.ironhorse.hub.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementação do Motor de Saúde e Alertas.
 * Cruza Hodômetro, Histórico e Metas Manuais para gerar diagnósticos proativos.
 */
@Service
public class AlertaService implements AlertaUseCases.ConfigurarAlerta, AlertaUseCases.ObterStatusSaude, AlertaUseCases.ListarConfiguracoes {

    private final AlertaConfigRepository alertaConfigRepository;
    private final ManutencaoRepository manutencaoRepository;
    private final MotoRepository motoRepository;

    public AlertaService(AlertaConfigRepository alertaConfigRepository, 
                         ManutencaoRepository manutencaoRepository, 
                         MotoRepository motoRepository) {
        this.alertaConfigRepository = alertaConfigRepository;
        this.manutencaoRepository = manutencaoRepository;
        this.motoRepository = motoRepository;
    }

    @Override
    @Transactional
    public void configurar(Long motoId, AlertaConfigInputDto input, String ownerEmail) {
        checkOwnership(motoId, ownerEmail);

        AlertaConfig config = alertaConfigRepository.findByMotoIdAndTipoServico(motoId, input.tipoServico())
                .orElse(new AlertaConfig());
        
        config.setMotoId(motoId);
        config.setTipoServico(input.tipoServico());
        config.setPeriodicidadeKm(input.periodicidadeKm());
        config.setAtivo(input.ativo());

        alertaConfigRepository.save(config);
    }

    @Override
    public StatusSaudeOutputDto obterStatus(Long motoId, String ownerEmail) {
        Moto moto = checkOwnership(motoId, ownerEmail);
        List<AlertaConfig> configs = alertaConfigRepository.findAllByMotoId(motoId);
        List<Manutencao> historico = manutencaoRepository.findAllByMotoIdOrderByDataServicoDesc(motoId);

        List<StatusSaudeOutputDto.ItemSaude> itens = new ArrayList<>();

        for (AlertaConfig config : configs) {
            if (!config.isAtivo()) continue;

            // Encontrar a última manutenção deste tipo
            Optional<Manutencao> ultimaManutencao = historico.stream()
                    .filter(m -> m.getTipoServico() == config.getTipoServico())
                    .findFirst();

            Double kmUltima = ultimaManutencao.map(Manutencao::getKm).orElse(0.0);
            Double kmProxima = kmUltima + config.getPeriodicidadeKm();
            Double kmRestante = kmProxima - moto.getKm();
            
            // Cálculo de percentual de vida útil (ex: 5000 periodicidade, 1000 rodado = 80%)
            Double kmRodadoDesdeUltima = moto.getKm() - kmUltima;
            Double percentualVidaUtil = Math.max(0, (1 - (kmRodadoDesdeUltima / config.getPeriodicidadeKm())) * 100);

            String statusVisual = "OK";
            if (percentualVidaUtil <= 0) statusVisual = "CRITICO";
            else if (percentualVidaUtil <= 10) statusVisual = "ALERTA";

            itens.add(new StatusSaudeOutputDto.ItemSaude(
                    config.getTipoServico(),
                    Math.max(0, kmRestante),
                    percentualVidaUtil,
                    statusVisual
            ));
        }

        return new StatusSaudeOutputDto(motoId, itens);
    }

    @Override
    public List<AlertaConfigInputDto> listar(Long motoId, String ownerEmail) {
        checkOwnership(motoId, ownerEmail);
        return alertaConfigRepository.findAllByMotoId(motoId).stream()
                .map(c -> new AlertaConfigInputDto(c.getTipoServico(), c.getPeriodicidadeKm(), c.isAtivo()))
                .collect(Collectors.toList());
    }

    private Moto checkOwnership(Long motoId, String ownerEmail) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new IllegalArgumentException("Motocicleta não encontrada"));

        if (!moto.getOwnerEmail().equals(ownerEmail)) {
            throw new SecurityException("Acesso negado à configuração desta máquina");
        }
        return moto;
    }
}
