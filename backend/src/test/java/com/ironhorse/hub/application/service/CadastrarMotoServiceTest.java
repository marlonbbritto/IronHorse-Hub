package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.dto.MotoInputDto;
import com.ironhorse.hub.application.dto.MotoOutputDto;
import com.ironhorse.hub.domain.Moto;
import com.ironhorse.hub.domain.MotoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

class CadastrarMotoServiceTest {

    @Test
    void shouldExecuteCadastroComSucesso() {
        // Mock do Repositório (Porta de Saída)
        MotoRepository repository = Mockito.mock(MotoRepository.class);
        
        // Mock do comportamento de salvamento
        String ownerEmail = "test@example.com";
        Moto motoSalva = new Moto(10L, "Dyna Super Glide", 2012, 45000.0, "KJB-9900", null, ownerEmail);
        Mockito.when(repository.save(any(Moto.class))).thenReturn(motoSalva);

        // Instanciação do Use Case (Application Service)
        CadastrarMotoService service = new CadastrarMotoService(repository);

        // Dado de Entrada (Input DTO)
        MotoInputDto inputDto = new MotoInputDto(
            "Dyna Super Glide", 2012, 45000.0, "KJB-9900", null
        );

        // Execução do Use Case
        MotoOutputDto result = service.execute(inputDto, ownerEmail);

        // Verificações
        assertNotNull(result);
        assertEquals(10L, result.id());
        assertEquals("Dyna Super Glide", result.modelo());
        Mockito.verify(repository, Mockito.times(1)).save(any(Moto.class));
    }
}
