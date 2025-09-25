package com.example.demo.dto.model.project;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SharedProjectResponse {
    private Long projectId;
    private Long userId;
    private String role;
    private String permission;
    private String message;
}