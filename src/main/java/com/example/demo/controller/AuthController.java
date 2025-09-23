package com.example.demo.controller;

import com.example.demo.dto.model.auth.LoginRequest;
import com.example.demo.dto.model.auth.LoginResponse;
import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.dto.model.auth.RegisterResponse;
import com.example.demo.dto.model.auth.UpdateProfileImageRequest;
import com.example.demo.dto.model.auth.UpdateUserRequest;
import com.example.demo.service.auth.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
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

    @PutMapping("/update/{username}")
    public RegisterResponse updateUser(
            @PathVariable String username,
            @RequestBody UpdateUserRequest updateRequest) {

        boolean actualizado = authService.actualizarUsuario(username, updateRequest);
        if (actualizado) {
            return new RegisterResponse(true, "✅ Usuario actualizado correctamente");
        } else {
            return new RegisterResponse(false, "❌ Usuario no encontrado o no se pudo actualizar");
        }
    }

    @PatchMapping("/update-profile-image/{username}")
    public RegisterResponse updateProfileImage(
            @PathVariable String username,
            @RequestBody UpdateProfileImageRequest request) {
        boolean actualizado = authService.actualizarImagen(username, request.getProfileImageUrl());
        if (actualizado) {
            return new RegisterResponse(true, "✅ Imagen de perfil actualizada correctamente");
        } else {
            return new RegisterResponse(false, "❌ Usuario no encontrado o no se pudo actualizar la imagen");
        }
    }

}
