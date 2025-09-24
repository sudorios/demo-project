package com.example.demo.dto.model.project;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectResponse {
    private Long id;
    private String projectCode;  
    private String icon;
    private String name;
    private String description;
    private Long statusId;
    private Long categoryId;
    private LocalDate startDate;
    private LocalDate endDate;
}
