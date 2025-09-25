package com.example.demo.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class AuthUtil {

    private final JdbcTemplate jdbcTemplate;

    public AuthUtil(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean usernameExists(String username) {
        String sql = "SELECT 1 FROM \"user\" WHERE username = ?";
        return !jdbcTemplate.queryForList(sql, username).isEmpty();
    }

    public boolean emailExists(String email) {
        String sql = "SELECT 1 FROM \"user\" WHERE email = ?";
        return !jdbcTemplate.queryForList(sql, email).isEmpty();
    }
}
