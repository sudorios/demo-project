package com.example.demo.repository.auth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ChangePasswordRepository {

    private final JdbcTemplate jdbcTemplate;

    public ChangePasswordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int updatePassword(Long userId, String nuevoPasswordHash) {
        String sql = """
            UPDATE "user"
            SET password_hash = ?, updated_at = NOW()
            WHERE id = ?
        """;

        return jdbcTemplate.update(sql, nuevoPasswordHash, userId);
    }
}
