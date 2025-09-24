package com.example.demo.service.project;

import org.springframework.stereotype.Service;

import com.example.demo.dto.model.project.ProjectRequest;
import com.example.demo.dto.model.project.ProjectResponse;
import com.example.demo.repository.Project.CreateProjectRepository;

@Service
public class ProjectService {

    private final CreateProjectRepository createProjectRepository;

    public ProjectService(CreateProjectRepository createProjectRepository) {
        this.createProjectRepository = createProjectRepository;
    }

    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        return createProjectRepository.createProject(request, userId);
    }
}
