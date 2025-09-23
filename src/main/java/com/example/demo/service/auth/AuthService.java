package com.example.demo.service.auth;

import com.example.demo.security.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.LoginResponse;
import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.dto.model.auth.UpdateUserRequest;
import com.example.demo.repository.auth.LoginRepository;
import com.example.demo.repository.auth.UserRepository;
import com.example.demo.repository.util.UserValidationUtil;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;

    private final LoginRepository loginRepository;
    private final UserRepository userRepository;
    private final UserValidationUtil userValidationUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(LoginRepository loginRepository, UserRepository userRepository,
            UserValidationUtil userValidationUtil, JwtUtil jwtUtil) {
        this.loginRepository = loginRepository;
        this.userRepository = userRepository;
        this.userValidationUtil = userValidationUtil;
        this.jwtUtil = jwtUtil;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        return loginRepository.findPasswordByUsername(loginRequest.getUsername())
                .map(storedPassword -> {
                    boolean valid = storedPassword.startsWith("$2a$")
                            ? passwordEncoder.matches(loginRequest.getPassword(), storedPassword)
                            : storedPassword.equals(loginRequest.getPassword());

                    if (valid) {
                        Long userId = loginRepository.findUserIdByUsername(loginRequest.getUsername())
                                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
                        String token = jwtUtil.generateToken(userId, 1000 * 60 * 60);
                        return new LoginResponse(true, "Login correcto", token);
                    }
                    return new LoginResponse(false, "❌ Credenciales inválidas", null);
                })
                .orElse(new LoginResponse(false, "❌ Credenciales inválidas", null));
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

    public boolean actualizarUsuario(String username, UpdateUserRequest request) {
        int rows = userRepository.updateUser(username, request);
        return rows > 0;
    }

    public boolean actualizarImagen(String username, String profileImageUrl) {
        int rows = userRepository.updateProfileImage(username, profileImageUrl);
        return rows > 0;
    }

}
