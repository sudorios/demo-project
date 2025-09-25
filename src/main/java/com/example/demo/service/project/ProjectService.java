package com.example.demo.service.project;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.model.project.ProjectRequest;
import com.example.demo.dto.model.project.ProjectResponse;
import com.example.demo.repository.project.CreateProjectRepository;
import com.example.demo.repository.project.SearchProjectRepository;

@Service
public class ProjectService {

    private final CreateProjectRepository createProjectRepository;
    private final SearchProjectRepository searchProjectRepository;

    public ProjectService(CreateProjectRepository createProjectRepository,
            SearchProjectRepository searchProjectRepository) {
        this.createProjectRepository = createProjectRepository;
        this.searchProjectRepository = searchProjectRepository;
    }

    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        return createProjectRepository.createProject(request, userId);
    }

    public List<ProjectResponse> searchProjects(
            String projectCode,
            String name,
            Long statusId,
            Long categoryId,
            String icon,
            LocalDate startDate,
            LocalDate endDate) {
        ProjectResponse filters = ProjectResponse.builder()
                .projectCode(projectCode)
                .name(name)
                .statusId(statusId)
                .categoryId(categoryId)
                .icon(icon)
                .startDate(startDate)
                .endDate(endDate)
                .build();
        return searchProjectRepository.searchProjects(filters);
    }
}
