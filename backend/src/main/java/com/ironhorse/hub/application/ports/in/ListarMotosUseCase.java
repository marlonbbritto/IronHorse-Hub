package com.ironhorse.hub.application.ports.in;

import com.ironhorse.hub.application.dto.MotoOutputDto;
import java.util.List;

/**
 * Interface de porta de entrada para o caso de uso de Listar Motos.
 */
public interface ListarMotosUseCase {
    List<MotoOutputDto> execute(String ownerEmail);
}
