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

    public Optional<String> findPasswordByEmail(String email) {
        String sql = "SELECT password_hash FROM \"user\" WHERE email = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getString("password_hash"),
                email).stream().findFirst();
    }

    public Optional<Long> findUserIdByEmail(String email) {
        String sql = "SELECT id FROM \"user\" WHERE email = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getLong("id"),
                email).stream().findFirst();
    }
}