package com.example.demo.dto.model.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ForgotPasswordResponse {
    private boolean success;
    private String message;
    private String resetToken;
}
