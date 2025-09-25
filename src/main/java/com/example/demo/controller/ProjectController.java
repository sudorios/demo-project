package com.example.demo.controller;

import com.example.demo.dto.model.project.ProjectRequest;
import com.example.demo.dto.model.project.ProjectResponse;
import com.example.demo.dto.model.project.SharedProjectRequest;
import com.example.demo.dto.model.project.SharedProjectResponse;
import com.example.demo.service.project.ProjectService;
import com.example.demo.service.project.ShareProjectService;
import com.example.demo.util.ProjectUtil;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

import java.time.LocalDate;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/projects")
@SecurityRequirement(name = "BearerAuth")
public class ProjectController {

    private final ProjectService projectService;
    private final ProjectUtil projectUtil;
    private final ShareProjectService shareProjectService;

    public ProjectController(ProjectService projectService, ProjectUtil projectUtil,
            ShareProjectService shareProjectService) {
        this.projectService = projectService;
        this.projectUtil = projectUtil;
        this.shareProjectService = shareProjectService;
    }

    @PostMapping
    public ProjectResponse createProject(@Valid @RequestBody ProjectRequest request) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return projectService.createProject(request, userId);
    }

    @GetMapping("/search")
    public List<ProjectResponse> searchProjects(
            @RequestParam(required = false) String projectCode,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) Long statusId,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String icon,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "true") boolean asc) {
        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<ProjectResponse> filteredProjects = projectService.searchProjects(
                userId, projectCode, name, statusId, categoryId, icon, startDate, endDate);
        return projectUtil.paginateAndSort(filteredProjects, page, size, sortBy, asc);
    }

    @PostMapping("/{projectId}/share")
    public SharedProjectResponse shareProject(
            @PathVariable Long projectId,
            @Valid @RequestBody SharedProjectRequest request) {

        Long loggedUserId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return shareProjectService.shareProject(loggedUserId, projectId, request);
    }
}
