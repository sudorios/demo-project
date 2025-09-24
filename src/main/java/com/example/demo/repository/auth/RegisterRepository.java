package com.example.demo.repository.auth;

import com.example.demo.dto.model.auth.RegisterRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class RegisterRepository {

    private final JdbcTemplate jdbcTemplate;

    public RegisterRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int registerUser(RegisterRequest request) {
        String sql = "INSERT INTO \"user\" " +
                "(username, email, password_hash, first_name, last_name, company_name, phone, \"position\", created_at, updated_at) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        String hashedPassword = request.getPassword();
        Timestamp now = new Timestamp(System.currentTimeMillis());

        return jdbcTemplate.update(sql,
                request.getUsername(),
                request.getEmail(),
                hashedPassword,
                request.getFirstName(),
                request.getLastName(),
                request.getCompanyName(),
                request.getPhone(),
                request.getPosition(),
                now,
                now);
    }
}
