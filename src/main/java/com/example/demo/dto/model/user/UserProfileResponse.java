package com.example.demo.dto.model.user;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                       
@NoArgsConstructor         
@AllArgsConstructor
@Builder      
public class UserProfileResponse {
    private String firstName;
    private String lastName;
    private String position;
    private String profileImageUrl;
    private List<Long> notificationIds;
}