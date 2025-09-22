package com.example.demo.controller;

import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.AuthResponse;
import com.example.demo.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody LoginRequest loginRequest) {
        boolean valido = authService.validarUsuario(loginRequest);
        if (valido) {
            return new AuthResponse(true, "✅ Login correcto para usuario: " + loginRequest.getUsername());
        } else {
            return new AuthResponse(false, "❌ Credenciales inválidas");
        }
    }
}
