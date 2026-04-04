package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.dto.ManutencaoInputDto;
import com.ironhorse.hub.application.dto.ManutencaoOutputDto;
import com.ironhorse.hub.application.ports.in.ManutencaoUseCases;
import com.ironhorse.hub.domain.Manutencao;
import com.ironhorse.hub.domain.ManutencaoRepository;
import com.ironhorse.hub.domain.Moto;
import com.ironhorse.hub.domain.MotoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementação dos serviços de Manutenção.
 * Valida a propriedade da moto antes de qualquer operação.
 */
@Service
public class ManutencaoService implements ManutencaoUseCases.RegistrarManutencao, ManutencaoUseCases.ListarHistorico {

    private final ManutencaoRepository manutencaoRepository;
    private final MotoRepository motoRepository;

    public ManutencaoService(ManutencaoRepository manutencaoRepository, MotoRepository motoRepository) {
        this.manutencaoRepository = manutencaoRepository;
        this.motoRepository = motoRepository;
    }

    @Override
    @Transactional
    public ManutencaoOutputDto execute(Long motoId, ManutencaoInputDto input, String ownerEmail) {
        checkOwnership(motoId, ownerEmail);

        Manutencao domain = new Manutencao(
                null,
                motoId,
                input.descritivo(),
                input.km(),
                input.dataServico(),
                input.custo(),
                input.prestador(),
                input.tipoServico()
        );

        Manutencao salva = manutencaoRepository.save(domain);
        return toDto(salva);
    }

    @Override
    public List<ManutencaoOutputDto> execute(Long motoId, String ownerEmail) {
        checkOwnership(motoId, ownerEmail);
        return manutencaoRepository.findAllByMotoIdOrderByDataServicoDesc(motoId).stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private Moto checkOwnership(Long motoId, String ownerEmail) {
        Moto moto = motoRepository.findById(motoId)
                .orElseThrow(() -> new IllegalArgumentException("Motocicleta não encontrada"));

        if (!moto.getOwnerEmail().equals(ownerEmail)) {
            throw new SecurityException("Você não tem permissão para acessar o histórico desta máquina");
        }
        return moto;
    }

    private ManutencaoOutputDto toDto(Manutencao domain) {
        return new ManutencaoOutputDto(
                domain.getId(),
                domain.getMotoId(),
                domain.getDescritivo(),
                domain.getKm(),
                domain.getDataServico(),
                domain.getCusto(),
                domain.getPrestador(),
                domain.getTipoServico()
        );
    }
}
