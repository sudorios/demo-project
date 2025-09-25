package com.example.demo.controller;

import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.model.board.BoardResponse;
import com.example.demo.service.board.BoardService;

@RestController
@RequestMapping("/api/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping
    public List<BoardResponse> getBoards(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Long userId = (Long) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return boardService.getBoardsByUser(userId, page, size);
    }
}
