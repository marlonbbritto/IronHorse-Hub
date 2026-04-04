package com.ironhorse.hub.application.service;

import com.ironhorse.hub.domain.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PasswordResetServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordResetTokenRepository tokenRepository;

    @Mock
    private EmailSender emailSender;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PasswordResetService passwordResetService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void request_ShouldCreateTokenAndSendEmail_WhenUserExists() {
        // Arrange
        String email = "test@example.com";
        User user = new User(UUID.randomUUID(), email, "hash");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(user));

        // Act
        passwordResetService.request(email);

        // Assert
        verify(tokenRepository, times(1)).deleteByUserId(user.getId());
        verify(tokenRepository, times(1)).save(any(PasswordResetToken.class));
        verify(emailSender, times(1)).sendPasswordResetEmail(eq(email), anyString());
    }

    @Test
    void reset_ShouldUpdatePassword_WhenTokenIsValid() {
        // Arrange
        String token = "valid-token";
        String newPassword = "newPassword123";
        UUID userId = UUID.randomUUID();
        PasswordResetToken resetToken = new PasswordResetToken(token, userId, LocalDateTime.now().plusMinutes(15));
        User user = new User(userId, "test@example.com", "oldHash");

        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(resetToken));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(passwordEncoder.encode(newPassword)).thenReturn("newHash");

        // Act
        passwordResetService.reset(token, newPassword);

        // Assert
        verify(userRepository, times(1)).save(argThat(updatedUser -> 
            updatedUser.getPasswordHash().equals("newHash")
        ));
        verify(tokenRepository, times(1)).deleteByUserId(userId);
    }

    @Test
    void reset_ShouldThrowException_WhenTokenIsExpired() {
        // Arrange
        String token = "expired-token";
        PasswordResetToken resetToken = new PasswordResetToken(token, UUID.randomUUID(), LocalDateTime.now().minusMinutes(1));
        when(tokenRepository.findByToken(token)).thenReturn(Optional.of(resetToken));

        // Act & Assert
        Exception exception = assertThrows(RuntimeException.class, () -> 
            passwordResetService.reset(token, "pass")
        );
        assertEquals("O token de recuperação expirou.", exception.getMessage());
    }
}
