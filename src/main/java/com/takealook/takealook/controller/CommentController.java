package com.takealook.takealook.controller;

import com.takealook.takealook.dto.CommentRequestDto;
import com.takealook.takealook.dto.CommentResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/board/{boardId}")
    public CommentResponseDto commentCreate(@PathVariable Long boardId,
                                            @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.createComment(boardId, commentRequestDto);
        return commentResponseDto;
    }

    @GetMapping("/board/{boardId}")
    public List<CommentResponseDto> commentRead(@PathVariable Long boardId,
                                                @RequestBody CommentRequestDto commentRequestDto) {
        List<CommentResponseDto> commentResponseDtoList = commentService.readComment(boardId, commentRequestDto);
        return commentResponseDtoList;
    }

    @PutMapping("/board/{boardId}/{commentId}")
    public CommentResponseDto commentPut(@PathVariable Long boardId,
                                           @PathVariable Long commentId,
                                           @RequestBody CommentRequestDto commentRequestDto) {
        CommentResponseDto commentResponseDto = commentService.putComment(boardId, commentId, commentRequestDto);
        return commentResponseDto;
    }

    @DeleteMapping("/board/{boardId}/{commentId}")
    public ResponseDto commentDelete(@PathVariable Long boardId, @PathVariable Long commentId) {
        return commentService.deleteComment(commentId);
    }
}