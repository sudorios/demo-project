package com.example.demo.service.user;

import org.springframework.stereotype.Service;

import com.example.demo.dto.model.user.UpdateUserRequest;
import com.example.demo.dto.model.user.UserProfileResponse;
import com.example.demo.repository.user.UpdateUserRepository;
import com.example.demo.repository.user.FindUserRepository;

@Service
public class UserService {

    private final UpdateUserRepository updateUserRepository;
    private final FindUserRepository findUserRepository;

    public UserService(UpdateUserRepository updateUserRepository, FindUserRepository findUserRepository) {
        this.updateUserRepository = updateUserRepository;
        this.findUserRepository = findUserRepository;
    }

    public boolean actualizarUsuario(Long userId, UpdateUserRequest request) {
        int rows = updateUserRepository.updateUser(userId, request);
        return rows > 0;
    }

    public boolean actualizarImagen(Long userId, String profileImageUrl) {
        int rows = updateUserRepository.updateProfileImage(userId, profileImageUrl);
        return rows > 0;
    }

    public UserProfileResponse getUserProfile(Long userId) {
        return findUserRepository.findUserProfile(userId);
    }
}
