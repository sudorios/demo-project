package com.example.demo.controller;

import com.example.demo.config.security.JwtUtil;
import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.LoginResponse;
import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.dto.model.auth.RegisterResponse;
import com.example.demo.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService, JwtUtil jwtUtil) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("/register")
    public RegisterResponse register(@RequestBody RegisterRequest registerRequest) {
        boolean registrado = authService.registrarUsuario(registerRequest);
        if (registrado) {
            return new RegisterResponse(true, "✅ Usuario registrado correctamente: " + registerRequest.getUsername());
        } else {
            return new RegisterResponse(false, "❌ El username o email ya existe");
        }
    }
}