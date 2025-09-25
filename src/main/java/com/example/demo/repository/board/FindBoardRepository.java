package com.example.demo.repository.board;

import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.example.demo.dto.model.board.BoardResponse;

@Repository
public class FindBoardRepository {

    private final JdbcTemplate jdbcTemplate;

    public FindBoardRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<BoardResponse> findBoardsByUser(Long userId, int limit, int offset) {
        String sql = "SELECT id, name, project_code, emoji, user_id, status_name " +
                "FROM board_view " +
                "WHERE user_id = ? " +
                "ORDER BY id DESC " +
                "LIMIT ? OFFSET ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> BoardResponse.builder()
                        .id(rs.getLong("id"))
                        .name(rs.getString("name"))
                        .projectCode(rs.getString("project_code"))
                        .emoji(rs.getString("emoji"))
                        .userId(rs.getLong("user_id"))
                        .statusName(rs.getString("status_name"))
                        .build(),
                userId, limit, offset);
    }
}
