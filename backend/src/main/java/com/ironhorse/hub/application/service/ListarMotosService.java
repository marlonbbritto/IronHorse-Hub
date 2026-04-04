package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.dto.MotoOutputDto;
import com.ironhorse.hub.application.ports.in.ListarMotosUseCase;
import com.ironhorse.hub.domain.MotoRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Serviço que implementa a listagem de motos por proprietário.
 */
@Service
public class ListarMotosService implements ListarMotosUseCase {

    private final MotoRepository motoRepository;

    public ListarMotosService(MotoRepository motoRepository) {
        this.motoRepository = motoRepository;
    }

    @Override
    public List<MotoOutputDto> execute(String ownerEmail) {
        return motoRepository.findAllByOwnerEmail(ownerEmail).stream()
            .map(moto -> new MotoOutputDto(
                moto.getId(),
                moto.getModelo(),
                moto.getAno(),
                moto.getKm(),
                moto.getPlaca(),
                moto.getVin(),
                moto.getOwnerEmail()
            ))
            .collect(Collectors.toList());
    }
}
