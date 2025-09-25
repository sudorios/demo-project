package com.example.demo.service.user;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.model.user.UpdateUserRequest;
import com.example.demo.dto.model.user.UserProfileResponse;
import com.example.demo.repository.user.UpdateUserRepository;
import com.example.demo.repository.notification.NotificationRepository;
import com.example.demo.repository.user.FindUserRepository;

@Service
public class UserService {

    private final UpdateUserRepository updateUserRepository;
    private final FindUserRepository findUserRepository;
    private final NotificationRepository notificationRepository;

    public UserService(UpdateUserRepository updateUserRepository, FindUserRepository findUserRepository, NotificationRepository notificationRepository) {
        this.updateUserRepository = updateUserRepository;
        this.findUserRepository = findUserRepository;
        this.notificationRepository = notificationRepository;
    }

    public boolean actualizarUsuario(Long userId, UpdateUserRequest request) {
        int rows = updateUserRepository.updateUser(userId, request);
        return rows > 0;
    }

    public boolean actualizarImagen(Long userId, String profileImageUrl) {
        int rows = updateUserRepository.updateProfileImage(userId, profileImageUrl);
        return rows > 0;
    }

    public UserProfileResponse getUserProfile(Long id) {
        UserProfileResponse userProfile = findUserRepository.findUserProfile(id);
        List<Long> notificationIds = notificationRepository.findNotification(id);
        userProfile.setNotificationIds(notificationIds);
        return userProfile;
    }
        
}
