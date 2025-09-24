package com.example.demo.repository.Project;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.model.project.ProjectRequest;
import com.example.demo.dto.model.project.ProjectResponse;
import com.example.demo.repository.util.ProjectUtil;

@Repository
public class CreateProjectRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProjectUtil projectUtil;

    public CreateProjectRepository(JdbcTemplate jdbcTemplate, ProjectUtil projectUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.projectUtil = projectUtil;
    }

    public ProjectResponse createProject(ProjectRequest request, Long userId) {
        String projectCode = projectUtil.generateProjectCode();

        String sql = "INSERT INTO projects (project_code, emoji, name, description, status_id, category_id, start_date, end_date, user_id) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?) RETURNING id";

        Long projectId = jdbcTemplate.queryForObject(
                sql,
                Long.class,
                projectCode,
                request.getIcon(),
                request.getName(),
                request.getDescription(),
                request.getStatusId(),
                request.getCategoryId(),
                request.getStartDate(),
                request.getEndDate(),
                userId);

        return ProjectResponse.builder()
                .id(projectId)
                .projectCode(projectCode)                
                .icon(request.getIcon())
                .name(request.getName())
                .description(request.getDescription())
                .statusId(request.getStatusId())
                .categoryId(request.getCategoryId())
                .startDate(request.getStartDate())
                .endDate(request.getEndDate())
                .build();
    }
}
