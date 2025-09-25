package com.example.demo.repository.Project;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.model.project.ProjectResponse;
import com.example.demo.filters.ProjectSpecification;

@Repository
public class SearchProjectRepository {

    private final JdbcTemplate jdbcTemplate;
    private final ProjectSpecification projectSpec;

    public SearchProjectRepository(JdbcTemplate jdbcTemplate, ProjectSpecification projectSpec) {
        this.jdbcTemplate = jdbcTemplate;
        this.projectSpec = projectSpec;
    }

    public List<ProjectResponse> searchProjects(ProjectResponse filters) {
        List<Object> params = new ArrayList<>();
        String sql = "SELECT id, project_code, name, emoji, description, status_id, category_id, start_date, end_date "
                + "FROM projects"
                + projectSpec.buildWhereClause(filters, params);

        return jdbcTemplate.query(sql, params.toArray(), (rs, rowNum) -> {
            java.sql.Date start = rs.getDate("start_date");
            java.sql.Date end = rs.getDate("end_date");

            return ProjectResponse.builder()
                    .id(rs.getLong("id"))
                    .projectCode(rs.getString("project_code"))
                    .name(rs.getString("name"))
                    .icon(rs.getString("emoji"))
                    .description(rs.getString("description"))
                    .statusId(rs.getLong("status_id"))
                    .categoryId(rs.getLong("category_id"))
                    .startDate(start != null ? start.toLocalDate() : null)
                    .endDate(end != null ? end.toLocalDate() : null)
                    .build();
        });
    }
}
