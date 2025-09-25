package com.example.demo.controller;

import com.example.demo.dto.model.auth.RegisterResponse;
import com.example.demo.dto.model.user.UpdateProfileImageRequest;
import com.example.demo.dto.model.user.UpdateUserRequest;
import com.example.demo.dto.model.user.UserProfileResponse;
import com.example.demo.service.user.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@SecurityRequirement(name = "BearerAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> getMyProfile() {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserProfileResponse profile = userService.getUserProfile(userId);

        if (profile != null) {
            return ResponseEntity.ok(profile);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/update")
    public RegisterResponse updateUser(@RequestBody UpdateUserRequest updateRequest) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean actualizado = userService.actualizarUsuario(userId, updateRequest);
        if (actualizado) {
            return new RegisterResponse(true, "✅ Usuario actualizado correctamente");
        } else {
            return new RegisterResponse(false, "❌ Usuario no encontrado o no se pudo actualizar");
        }
    }

    @PatchMapping("/update-profile-image")
    public RegisterResponse updateProfileImage(@RequestBody UpdateProfileImageRequest request) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        boolean actualizado = userService.actualizarImagen(userId, request.getProfileImageUrl());
        if (actualizado) {
            return new RegisterResponse(true, "✅ Imagen de perfil actualizada correctamente");
        } else {
            return new RegisterResponse(false, "❌ Usuario no encontrado o no se pudo actualizar la imagen");
        }
    }
}
