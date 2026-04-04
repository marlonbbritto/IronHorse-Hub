package com.ironhorse.hub.application.ports.in;

import com.ironhorse.hub.application.dto.HodometroInputDto;
import com.ironhorse.hub.application.dto.MotoOutputDto;

/**
 * Interface de porta de entrada para o caso de uso de atualização de hodômetro.
 */
public interface AtualizarHodometroUseCase {
    MotoOutputDto execute(Long id, HodometroInputDto input, String ownerEmail);
}
