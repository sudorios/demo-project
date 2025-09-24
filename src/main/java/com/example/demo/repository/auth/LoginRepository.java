package com.example.demo.repository.auth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class LoginRepository {
    private final JdbcTemplate jdbcTemplate;

    public LoginRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<String> findPasswordByUsername(String username) {
        String sql = "SELECT password_hash FROM \"user\" WHERE username = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getString("password_hash"),
                username).stream().findFirst();
    }

    public Optional<Long> findUserIdByUsername(String username) {
        String sql = "SELECT id FROM \"user\" WHERE username = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getLong("id"),
                username).stream().findFirst();
    }
}