package com.example.demo.repository.auth;

import java.util.Optional;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ForgotPasswordRepository {

    private final JdbcTemplate jdbcTemplate;

    public ForgotPasswordRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<Long> findUserIdByEmail(String email) {
        String sql = "SELECT id FROM \"user\" WHERE email = ?";
        return jdbcTemplate.query(sql, ps -> ps.setString(1, email), rs -> {
            if (rs.next()) {
                return Optional.of(rs.getLong("id"));
            }
            return Optional.empty();
        });
    }
}
