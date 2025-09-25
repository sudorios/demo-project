package com.example.demo.filters;

import com.example.demo.dto.model.project.ProjectResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProjectSpecification {

    public String buildWhereClause(ProjectResponse filters, List<Object> params) {
        StringBuilder sql = new StringBuilder(" WHERE 1=1");

        if (filters.getProjectCode() != null && !filters.getProjectCode().isEmpty()) {
            sql.append(" AND project_code LIKE ?");
            params.add("%" + filters.getProjectCode() + "%");
        }

        if (filters.getName() != null && !filters.getName().isEmpty()) {
            sql.append(" AND name LIKE ?");
            params.add("%" + filters.getName() + "%");
        }

        if (filters.getStatusId() != null) {
            sql.append(" AND status_id = ?");
            params.add(filters.getStatusId());
        }

        if (filters.getCategoryId() != null) {
            sql.append(" AND category_id = ?");
            params.add(filters.getCategoryId());
        }

        if (filters.getIcon() != null && !filters.getIcon().isEmpty()) {
            sql.append(" AND icon LIKE ?");
            params.add("%" + filters.getIcon() + "%");
        }

        if (filters.getStartDate() != null) {
            sql.append(" AND start_date >= ?");
            params.add(filters.getStartDate());
        }

        if (filters.getEndDate() != null) {
            sql.append(" AND end_date <= ?");
            params.add(filters.getEndDate());
        }

        return sql.toString();
    }
}
