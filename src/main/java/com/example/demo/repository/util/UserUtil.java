package com.example.demo.repository.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserUtil {

    private final JdbcTemplate jdbcTemplate;

    public UserUtil(JdbcTemplate jdbcTemplate) {
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
    
    public String extractFirstWord(String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return value.split(" ")[0];
    }


}
