package com.example.demo.repository.user;

import com.example.demo.dto.model.user.UserProfileResponse;
import com.example.demo.util.UserUtil;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class FindUserRepository {

    private final JdbcTemplate jdbcTemplate;
    private final UserUtil userUtil;
    
    public FindUserRepository(JdbcTemplate jdbcTemplate, UserUtil userUtil) {
        this.jdbcTemplate = jdbcTemplate;
        this.userUtil = userUtil;
    }

    public UserProfileResponse findUserProfile(Long id) {
        String sql = "SELECT first_name, last_name, position, profile_image_url FROM \"user\" WHERE id = ?";

        RowMapper<UserProfileResponse> rowMapper = (rs, rowNum) -> UserProfileResponse.builder()
                .firstName(userUtil.extractFirstWord(rs.getString("first_name")))
                .lastName(userUtil.extractFirstWord(rs.getString("last_name")))
                .position(rs.getString("position"))
                .profileImageUrl(rs.getString("profile_image_url"))
                .build();

        return jdbcTemplate.queryForObject(sql, rowMapper, id);
    }

}
