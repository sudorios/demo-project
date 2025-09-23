package com.example.demo.dto.model.auth;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String companyName;
    private String phone;  
    private String position; 
}
