package com.example.demo.service.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.repository.auth.UserRepository;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean validarUsuario(LoginRequest loginRequest) {
        return userRepository.findPasswordByUsername(loginRequest.getUsername())
                .map(storedPassword -> {
                    if (storedPassword.startsWith("$2a$")) {
                        return passwordEncoder.matches(loginRequest.getPassword(), storedPassword);
                    }
                    return storedPassword.equals(loginRequest.getPassword());
                })
                .orElse(false);
    }
}
