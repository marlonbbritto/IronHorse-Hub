package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.ports.in.AuthenticateUserUseCase;
import com.ironhorse.hub.domain.AuthToken;
import com.ironhorse.hub.domain.TokenProvider;
import com.ironhorse.hub.domain.User;
import com.ironhorse.hub.domain.UserRepository;
import com.ironhorse.hub.infrastructure.exception.AuthenticationFailedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço de aplicação que implementa o caso de uso de autenticação.
 */
@Service
public class AuthenticateUserService implements AuthenticateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public AuthenticateUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public AuthToken authenticate(String email, String password) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new AuthenticationFailedException("Credenciais inválidas."));

        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new AuthenticationFailedException("Credenciais inválidas.");
        }

        return tokenProvider.generateToken(user);
    }
}
