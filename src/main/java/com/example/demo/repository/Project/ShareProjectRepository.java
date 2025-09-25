package com.example.demo.repository.project;

import com.example.demo.dto.model.project.SharedProjectRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ShareProjectRepository {

    private final JdbcTemplate jdbcTemplate;

    public ShareProjectRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void shareProject(Long projectId, SharedProjectRequest req) {
        String sql = """
            INSERT INTO shared_projects (project_id, user_id, permission_type_id, role_id)
            VALUES (?, ?, 
                (SELECT id FROM permission_types WHERE code = ?), ?)
            ON CONFLICT (project_id, user_id) 
            DO UPDATE SET 
                permission_type_id = EXCLUDED.permission_type_id,
                role_id = EXCLUDED.role_id
        """;

        jdbcTemplate.update(sql,
                projectId,
                req.getUserId(),
                req.getPermissionCode(),
                req.getRoleId());
    }
}
