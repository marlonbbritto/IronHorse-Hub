package com.ironhorse.hub.infrastructure.adapters.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhorse.hub.application.ports.in.RegisterUserUseCase;
import com.ironhorse.hub.domain.TokenProvider;
import com.ironhorse.hub.domain.User;
import com.ironhorse.hub.domain.UserRepository;
import com.ironhorse.hub.infrastructure.config.JwtAuthenticationFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
class RegisterControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private RegisterUserUseCase registerUserUseCase;

    // Mocks para o Security Context
    @MockitoBean
    private TokenProvider tokenProvider;
    @MockitoBean
    private JwtAuthenticationFilter jwtAuthFilter;
    @MockitoBean
    private UserRepository userRepository;

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        UUID userId = UUID.randomUUID();
        User user = new User(userId, "test@example.com", "encodedPassword");
        when(registerUserUseCase.register(anyString(), anyString())).thenReturn(user);

        Map<String, String> request = Map.of(
            "email", "test@example.com",
            "password", "password123"
        );

        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(userId.toString()))
                .andExpect(jsonPath("$.email").value("test@example.com"));
    }

    @Test
    void shouldReturnBadRequestWhenRegistrationInputIsInvalid() throws Exception {
        Map<String, String> request = Map.of(
            "email", "invalid-email",
            "password", ""
        );

        mockMvc.perform(post("/api/v1/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());
    }
}
