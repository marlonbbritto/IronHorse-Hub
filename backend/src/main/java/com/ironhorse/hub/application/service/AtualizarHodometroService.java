package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.dto.HodometroInputDto;
import com.ironhorse.hub.application.dto.MotoOutputDto;
import com.ironhorse.hub.application.ports.in.AtualizarHodometroUseCase;
import com.ironhorse.hub.domain.Moto;
import com.ironhorse.hub.domain.MotoRepository;
import org.springframework.stereotype.Service;

/**
 * Implementação do caso de uso de atualização de hodômetro.
 * Valida a existência, propriedade e aplica a lógica de domínio.
 */
@Service
public class AtualizarHodometroService implements AtualizarHodometroUseCase {

    private final MotoRepository motoRepository;

    public AtualizarHodometroService(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @Override
    public MotoOutputDto execute(Long id, HodometroInputDto input, String ownerEmail) {
        Moto moto = motoRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Motocicleta não encontrada"));

        // Garantir que o piloto logado é o dono da máquina
        if (!moto.getOwnerEmail().equals(ownerEmail)) {
            throw new SecurityException("Você não tem permissão para alterar esta máquina");
        }

        // Aplicação da regra de negócio (Validação de retrocesso KM + Data Atualização)
        moto.atualizarKm(input.km());

        // Operação de persistência via porta de saída
        Moto motoSalva = motoRepository.save(moto);

        // Retorno formatado em DTO de Saída
        return new MotoOutputDto(
            motoSalva.getId(),
            motoSalva.getModelo(),
            motoSalva.getAno(),
            motoSalva.getKm(),
            motoSalva.getPlaca(),
            motoSalva.getVin(),
            motoSalva.getOwnerEmail(),
            motoSalva.getDataUltimaAtualizacao()
        );
    }
}
