package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhorse.hub.application.dto.PasswordResetConfirmDTO;
import com.ironhorse.hub.application.dto.PasswordResetRequestDTO;
import com.ironhorse.hub.application.ports.in.RequestPasswordResetUseCase;
import com.ironhorse.hub.application.ports.in.ResetPasswordUseCase;
import com.ironhorse.hub.domain.TokenProvider;
import com.ironhorse.hub.domain.UserRepository;
import com.ironhorse.hub.infrastructure.config.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PasswordResetController.class)
@AutoConfigureMockMvc(addFilters = false)
class PasswordResetIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RequestPasswordResetUseCase requestUseCase;

    @MockBean
    private ResetPasswordUseCase resetUseCase;

    // Mocks para o Security Context
    @MockBean
    private TokenProvider tokenProvider;
    @MockBean
    private JwtAuthenticationFilter jwtAuthFilter;
    @MockBean
    private UserRepository userRepository;

    @Test
    void shouldRequestPasswordResetSuccessfully() throws Exception {
        doNothing().when(requestUseCase).request(anyString());

        PasswordResetRequestDTO request = new PasswordResetRequestDTO("test@example.com");

        mockMvc.perform(post("/api/v1/users/password-reset/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldConfirmPasswordResetSuccessfully() throws Exception {
        doNothing().when(resetUseCase).reset(anyString(), anyString());

        PasswordResetConfirmDTO confirm = new PasswordResetConfirmDTO("token-123", "newPassword123");

        mockMvc.perform(post("/api/v1/users/password-reset/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(confirm)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnBadRequestWhenRequestInputIsInvalid() throws Exception {
        PasswordResetRequestDTO request = new PasswordResetRequestDTO("invalid-email");

        mockMvc.perform(post("/api/v1/users/password-reset/request")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void shouldReturnBadRequestWhenConfirmInputIsInvalid() throws Exception {
        PasswordResetConfirmDTO confirm = new PasswordResetConfirmDTO("", "123"); // Senha curta e token vazio

        mockMvc.perform(post("/api/v1/users/password-reset/confirm")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(confirm)))
                .andExpect(status().isBadRequest());
    }
}
