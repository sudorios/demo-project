package com.example.demo.controller;

import com.example.demo.dto.model.project.ProjectRequest;
import com.example.demo.dto.model.project.ProjectResponse;
import com.example.demo.service.project.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }
    
    @PostMapping
    public ProjectResponse createProject(
            @Valid @RequestBody ProjectRequest request,
            @RequestParam Long userId) {
        return projectService.createProject(request, userId);
    }
}
