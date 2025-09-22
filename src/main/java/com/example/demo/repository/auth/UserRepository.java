package com.example.demo.repository.auth;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<String> findPasswordByUsername(String username) {
        String sql = "SELECT password FROM \"user\" WHERE username = ?";
        return jdbcTemplate.query(sql,
                (rs, rowNum) -> rs.getString("password"),
                username).stream().findFirst();
    }
}
