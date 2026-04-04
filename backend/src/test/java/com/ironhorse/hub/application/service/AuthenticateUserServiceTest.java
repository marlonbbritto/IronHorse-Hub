package com.ironhorse.hub.application.service;

import com.ironhorse.hub.domain.AuthToken;
import com.ironhorse.hub.domain.TokenProvider;
import com.ironhorse.hub.domain.User;
import com.ironhorse.hub.domain.UserRepository;
import com.ironhorse.hub.infrastructure.exception.AuthenticationFailedException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthenticateUserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenProvider tokenProvider;

    @InjectMocks
    private AuthenticateUserService authenticateUserService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void authenticate_ShouldReturnToken_WhenCredentialsAreValid() {
        // Arrange
        String email = "user@example.com";
        String password = "password123";
        String passwordHash = "encodedPassword";
        User user = new User(UUID.randomUUID(), email, passwordHash);
        AuthToken expectedToken = new AuthToken("jwt-token");

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, passwordHash)).thenReturn(true);
        when(tokenProvider.generateToken(user)).thenReturn(expectedToken);

        // Act
        AuthToken result = authenticateUserService.authenticate(email, password);

        // Assert
        assertNotNull(result);
        assertEquals("jwt-token", result.token());
        verify(tokenProvider, times(1)).generateToken(user);
    }

    @Test
    void authenticate_ShouldThrowException_WhenUserNotFound() {
        // Arrange
        String email = "notfound@example.com";
        when(userRepository.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(AuthenticationFailedException.class, () -> 
            authenticateUserService.authenticate(email, "any")
        );
    }

    @Test
    void authenticate_ShouldThrowException_WhenPasswordIsIncorrect() {
        // Arrange
        String email = "user@example.com";
        String password = "wrongpassword";
        String passwordHash = "encodedPassword";
        User user = new User(UUID.randomUUID(), email, passwordHash);

        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(password, passwordHash)).thenReturn(false);

        // Act & Assert
        assertThrows(AuthenticationFailedException.class, () -> 
            authenticateUserService.authenticate(email, password)
        );
    }
}
