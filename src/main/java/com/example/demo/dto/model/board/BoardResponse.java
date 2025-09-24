package com.example.demo.dto.model.board;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor         
@AllArgsConstructor
@Builder

public class BoardResponse {
    private Long id;
    private String name;
    private String projectCode;
    private String emoji;
    private Long userId;
    private String statusName;
}
