package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.ports.in.RequestPasswordResetUseCase;
import com.ironhorse.hub.application.ports.in.ResetPasswordUseCase;
import com.ironhorse.hub.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Serviço que orquestra o fluxo de recuperação de senha.
 */
@Service
public class PasswordResetService implements RequestPasswordResetUseCase, ResetPasswordUseCase {

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailSender emailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(UserRepository userRepository, 
                              PasswordResetTokenRepository tokenRepository, 
                              EmailSender emailSender, 
                              PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void request(String email) {
        userRepository.findByEmail(email).ifPresent(user -> {
            // Remove tokens anteriores do mesmo usuário
            tokenRepository.deleteByUserId(user.getId());

            // Gera novo token
            String token = UUID.randomUUID().toString();
            PasswordResetToken resetToken = new PasswordResetToken(
                token, 
                user.getId(), 
                LocalDateTime.now().plusMinutes(15)
            );

            tokenRepository.save(resetToken);
            emailSender.sendPasswordResetEmail(user.getEmail(), token);
        });
        // CT-01: Sempre retorna sucesso (implícito no void e status 200) para evitar User Enumeration
    }

    @Override
    @Transactional
    public void reset(String token, String newPassword) {
        PasswordResetToken resetToken = tokenRepository.findByToken(token)
            .orElseThrow(() -> new RuntimeException("Token inválido ou não encontrado."));

        if (resetToken.isExpired()) {
            throw new RuntimeException("O token de recuperação expirou.");
        }

        User user = userRepository.findById(resetToken.getUserId())
            .orElseThrow(() -> new RuntimeException("Usuário não encontrado."));

        // Atualiza a senha
        User updatedUser = new User(
            user.getId(), 
            user.getEmail(), 
            passwordEncoder.encode(newPassword)
        );

        userRepository.save(updatedUser);
        tokenRepository.deleteByUserId(user.getId());
    }
}
