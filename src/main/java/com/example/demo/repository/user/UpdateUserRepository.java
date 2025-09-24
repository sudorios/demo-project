package com.example.demo.repository.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.demo.dto.model.user.UpdateUserRequest;

import java.sql.Timestamp;

@Repository
public class UpdateUserRepository {

    private final JdbcTemplate jdbcTemplate;

    public UpdateUserRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
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
