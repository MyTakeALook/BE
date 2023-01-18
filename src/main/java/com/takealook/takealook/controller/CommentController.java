package com.takealook.takealook.controller;

import com.takealook.takealook.dto.CommentRequestDto;
import com.takealook.takealook.dto.CommentResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.security.UserDetailsImpl;
import com.takealook.takealook.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;
    @PostMapping("/board/{boardId}") // @RequestBody
    public CommentResponseDto commentCreate(@PathVariable Long boardId,
                                            @RequestBody CommentRequestDto commentRequestDto,
                                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        System.out.println("1111111111111111111111111111111111");
        System.out.println(boardId);
        System.out.println(commentRequestDto);
        System.out.println(userDetailsImpl.getUser().getUsername());
        System.out.println("1111111111111111111111111111111111");
        return commentService.createComment(boardId, commentRequestDto, userDetailsImpl);
    }

    @GetMapping("/board/{boardId}")
    public List<CommentResponseDto> commentRead(@PathVariable Long boardId) {
        return commentService.readCommentList(boardId);
    }

    @PatchMapping("/board/{boardId}/{commentId}") // @RequestBody
    public CommentResponseDto commentPatch(@PathVariable Long boardId,
                                           @PathVariable Long commentId,
                                           @RequestBody CommentRequestDto commentRequestDto,
                                           @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.putComment(boardId, commentId, commentRequestDto, userDetailsImpl);
    }

    @DeleteMapping("/board/{boardId}/{commentId}")
    public ResponseDto commentDelete(@PathVariable Long boardId,
                                     @PathVariable Long commentId,
                                     @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return commentService.deleteComment(commentId, userDetailsImpl);
    }
}
