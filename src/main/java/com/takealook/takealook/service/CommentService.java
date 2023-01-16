package com.takealook.takealook.service;

import com.takealook.takealook.dto.BoardResponseDto;
import com.takealook.takealook.dto.CommentRequestDto;
import com.takealook.takealook.dto.CommentResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.entity.Board;
import com.takealook.takealook.entity.Comment;
import com.takealook.takealook.repository.BoardRepository;
import com.takealook.takealook.repository.CommentRepository;
import com.takealook.takealook.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    @Transactional
    public CommentResponseDto createComment(Long boardId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetailsImpl) {
        Board board = boardRepository.findById(boardId).orElseThrow(
                () -> new IllegalArgumentException("게시판이 존재하지 않습니다.")
        );
        Comment comment = new Comment(commentRequestDto, board, userDetailsImpl.getUser());
        commentRepository.save(comment);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    public List<CommentResponseDto> readCommentList(Long boardId) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);

        List<Comment> commentList = commentRepository.findAllByBoardOrderByModifiedAtAsc(board.get()); // 복수형
        List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            if (comment.isDelete()) {
                continue;
            }
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            commentResponseDtoList.add(commentResponseDto);
        }

        return commentResponseDtoList;
    }

    @Transactional
    public CommentResponseDto putComment(Long boardId, Long commentId, CommentRequestDto commentRequestDto, UserDetailsImpl userDetailsImpl) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (!comment.getUser().getId().equals(userDetailsImpl.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        comment.CommentPut(commentRequestDto);
        CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
        return commentResponseDto;
    }

    @Transactional
    public ResponseDto deleteComment(Long commentId, UserDetailsImpl userDetailsImpl) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글이 존재하지 않습니다.")
        );
        if (comment.isDelete() == false) {
            comment.CommentDelete();
        } else {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        if (!comment.getUser().getId().equals(userDetailsImpl.getUser().getId())) {
            throw new IllegalArgumentException("권한이 없습니다.");
        }
        ResponseDto responseDto = new ResponseDto();
        responseDto.ResponseTrue();
        return responseDto;
    }
}
