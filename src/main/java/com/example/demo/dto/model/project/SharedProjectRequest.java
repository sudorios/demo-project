package com.example.demo.dto.model.project;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharedProjectRequest {
    private Long userId;
    private String permissionCode; 
    private Long roleId;          
}

