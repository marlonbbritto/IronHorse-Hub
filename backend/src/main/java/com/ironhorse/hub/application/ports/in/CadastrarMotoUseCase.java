package com.ironhorse.hub.application.ports.in;

import com.ironhorse.hub.application.dto.MotoInputDto;
import com.ironhorse.hub.application.dto.MotoOutputDto;

/**
 * Interface de porta de entrada para o caso de uso de Cadastrar Moto.
 * Segue os princípios da Arquitetura Hexagonal.
 */
public interface CadastrarMotoUseCase {
    MotoOutputDto execute(MotoInputDto input, String ownerEmail);
}
