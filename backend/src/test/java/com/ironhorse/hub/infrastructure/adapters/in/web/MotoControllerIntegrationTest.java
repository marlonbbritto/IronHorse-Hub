package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhorse.hub.application.dto.MotoInputDto;
import com.ironhorse.hub.application.ports.in.CadastrarMotoUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MotoController.class)
@SuppressWarnings("null")
class MotoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CadastrarMotoUseCase cadastrarMotoUseCase;

    @Test
    void shouldReturnBadRequestWhenInputIsInvalid() throws Exception {
        // Dado de entrada inválido (sem modelo)
        MotoInputDto invalidInput = new MotoInputDto("", 2024, 0.0, null, null);

        mockMvc.perform(post("/api/v1/motos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidInput)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnCreatedWhenInputIsValid() throws Exception {
        // Dado de entrada válido
        MotoInputDto validInput = new MotoInputDto("Iron 883", 2024, 0.0, "ABC-1234", "VIN123");

        mockMvc.perform(post("/api/v1/motos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(validInput)))
                .andExpect(status().isCreated());
    }
}
