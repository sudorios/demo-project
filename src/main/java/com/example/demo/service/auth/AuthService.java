package com.example.demo.service.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.security.JwtUtil;
import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.LoginResponse;
import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.repository.auth.LoginRepository;
import com.example.demo.repository.auth.RegisterRepository;
import com.example.demo.util.UserUtil;

@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final LoginRepository loginRepository;
    private final RegisterRepository registerRepository;
    private final UserUtil userUtil;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public AuthService(LoginRepository loginRepository, RegisterRepository registerRepository,
            UserUtil userUtil, JwtUtil jwtUtil) {
        this.loginRepository = loginRepository;
        this.registerRepository = registerRepository;
        this.userUtil = userUtil;
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
                        String token = jwtUtil.generateToken(userId, 1000 * 60 * 60); // 1 hora
                        return new LoginResponse(true, "✅ Login correcto", token);
                    }
                    return new LoginResponse(false, "❌ Credenciales inválidas", null);
                })
                .orElse(new LoginResponse(false, "❌ Credenciales inválidas", null));
    }

    public boolean registrarUsuario(RegisterRequest request) {
        if (userUtil.usernameExists(request.getUsername()) ||
                userUtil.emailExists(request.getEmail())) {
            return false;
        }
        request.setPassword(passwordEncoder.encode(request.getPassword()));
        int rows = registerRepository.registerUser(request);
        return rows > 0;
    }

}