package com.example.demo.repository.auth;

import com.example.demo.dto.model.auth.RegisterRequest;
import com.example.demo.dto.model.auth.UpdateUserRequest;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;

@Repository
public class UserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserRepository(JdbcTemplate jdbcTemplate) {
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

    public int updateUser(Long userId, UpdateUserRequest request) {
        String sql = "UPDATE \"user\" SET " +
                "first_name = ?, " +
                "last_name = ?, " +
                "company_name = ?, " +
                "phone = ?, " +
                "\"position\" = ?, " +
                "updated_at = ? " +
                "WHERE id = ?";

        Timestamp now = new Timestamp(System.currentTimeMillis());

        return jdbcTemplate.update(sql,
                request.getFirstName(),
                request.getLastName(),
                request.getCompanyName(),
                request.getPhone(),
                request.getPosition(),
                now,
                userId);
    }

    public int updateProfileImage(Long userId, String profileImageUrl) {
        String sql = "UPDATE \"user\" SET profile_image_url = ?, updated_at = ? WHERE id = ?";
        Timestamp now = new Timestamp(System.currentTimeMillis());

        return jdbcTemplate.update(sql, profileImageUrl, now, userId);
    }
}
