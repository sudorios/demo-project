package com.example.demo.service.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.repository.auth.LoginRepository;
import com.example.demo.repository.auth.UserRepository;
import com.example.demo.repository.util.UserValidationUtil;

@Service
public class AuthService {

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final UserValidationUtil userValidationUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(LoginRepository loginRepository, UserRepository userRepository, UserValidationUtil userValidationUtil) {
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
        this.userValidationUtil = userValidationUtil;
    }

    public boolean validarUsuario(LoginRequest loginRequest) {
        return loginRepository.findPasswordByUsername(loginRequest.getUsername())
                .map(storedPassword -> {
                    if (storedPassword.startsWith("$2a$")) {
                        return passwordEncoder.matches(loginRequest.getPassword(), storedPassword);
                    }
                    return storedPassword.equals(loginRequest.getPassword());
                })
                .orElse(false);
    }

    public boolean registrarUsuario(RegisterRequest request) {
        if (userValidationUtil.usernameExists(request.getUsername()) || 
            userValidationUtil.emailExists(request.getEmail())) {
            return false;
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        int rows = userRepository.registerUser(request);
        return rows > 0;
    }
}

