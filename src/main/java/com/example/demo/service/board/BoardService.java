package com.example.demo.service.board;

import java.util.List;
import org.springframework.stereotype.Service;
import com.example.demo.dto.model.board.BoardResponse;
import com.example.demo.repository.board.FindBoardRepository;

@Service
public class BoardService {

    private final FindBoardRepository findBoardRepository;

    public BoardService(FindBoardRepository findBoardRepository) {
        this.findBoardRepository = findBoardRepository;
    }

    public List<BoardResponse> getBoardsByUser(Long userId, int page, int size) {
        int offset = page * size;
        return findBoardRepository.findBoardsByUser(userId, size, offset);
    }
}
