package com.example.demo.dto.model.auth;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChangePasswordRequest implements Serializable {
    
    private static final long serialVersionUID = 1621382588993699833L;
    private String newPassword;
    private String confirmPassword;
    private String token;
}

