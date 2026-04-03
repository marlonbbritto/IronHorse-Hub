package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.dto.MotoInputDto;
import com.ironhorse.hub.application.dto.MotoOutputDto;
import com.ironhorse.hub.application.ports.in.CadastrarMotoUseCase;
import com.ironhorse.hub.domain.Moto;
import com.ironhorse.hub.domain.MotoRepository;
import org.springframework.stereotype.Service;

/**
 * Implementação do caso de uso de cadastro de motos.
 * Segue os princípios da Arquitetura Hexagonal sendo o principal orquestrador
 * entre a entrada de dados e o repositório de domínio.
 */
@Service
public class CadastrarMotoService implements CadastrarMotoUseCase {

    private final MotoRepository motoRepository;

    public CadastrarMotoService(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @Override
    public MotoOutputDto execute(MotoInputDto input) {
        // Conversão do DTO para Entidade de Domínio
        Moto moto = new Moto(
            null,
            input.modelo(),
            input.ano(),
            input.km(),
            input.placa(),
            input.vin()
        );

        // Operação de persistência via porta de saída
        Moto motoSalva = motoRepository.save(moto);

        // Retorno formatado em DTO de Saída
        return new MotoOutputDto(
            motoSalva.getId(),
            motoSalva.getModelo(),
            motoSalva.getAno(),
            motoSalva.getKm(),
            motoSalva.getPlaca(),
            motoSalva.getVin()
        );
    }
}
