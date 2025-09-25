package com.example.demo.controller;

import com.example.demo.config.security.JwtUtil;
import com.example.demo.dto.model.auth.ChangePasswordRequest;
import com.example.demo.dto.model.auth.ForgotPasswordRequest;
import com.example.demo.dto.model.auth.ForgotPasswordResponse;
import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.LoginResponse;
import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.dto.model.auth.RegisterResponse;
import com.example.demo.service.auth.AuthService;

import jakarta.validation.Valid;

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

    @PostMapping("/update-password")
    public RegisterResponse cambiarPassword(@RequestBody ChangePasswordRequest request) {
        boolean actualizado = authService.updatePassword(request);

        if (actualizado) {
            return new RegisterResponse(true, "✅ Contraseña actualizada correctamente");
        } else {
            return new RegisterResponse(false, "❌ No se pudo actualizar la contraseña");
        }
    }

    @PostMapping("/forgot-password")
    public ForgotPasswordResponse forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        return authService.forgotPassword(request);
    }
}