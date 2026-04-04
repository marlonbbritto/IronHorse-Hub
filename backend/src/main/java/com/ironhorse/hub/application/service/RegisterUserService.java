package com.ironhorse.hub.application.service;

import com.ironhorse.hub.application.ports.in.RegisterUserUseCase;
import com.ironhorse.hub.domain.User;
import com.ironhorse.hub.domain.UserRepository;
import com.ironhorse.hub.infrastructure.exception.EmailAlreadyExistsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço de aplicação que implementa o caso de uso de registro de usuário.
 */
@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String email, String password) {
        if (userRepository.findByEmail(email).isPresent()) {
            throw new EmailAlreadyExistsException("Usuário já cadastrado com este e-mail.");
        }

        String encodedPassword = passwordEncoder.encode(password);
        User newUser = new User(null, email, encodedPassword);
        
        return userRepository.save(newUser);
    }
}
