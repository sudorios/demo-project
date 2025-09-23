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
public class RegisterResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean success;
    private String message;
}
