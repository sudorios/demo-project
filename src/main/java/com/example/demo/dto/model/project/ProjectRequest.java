package com.example.demo.dto.model.project;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ProjectRequest {

    @NotBlank(message = "El icono es obligatorio")
    private String icon;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "El nombre no puede superar los 100 caracteres")
    private String name;

    @Size(max = 255, message = "La descripción no puede superar los 255 caracteres")
    private String description;

    @NotNull(message = "El estado es obligatorio")
    private Long statusId;

    @NotNull(message = "La categoría es obligatoria")
    private Long categoryId;

    private LocalDate startDate;
    private LocalDate endDate;
}
