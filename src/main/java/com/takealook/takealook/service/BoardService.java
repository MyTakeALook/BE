package com.takealook.takealook.service;

import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.dto.BoardResponseDto;
import com.takealook.takealook.entity.Board;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    @Transactional
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto) {
        Board board = new Board(boardRequestDto);
        boardRepository.save(board);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }

    public List<BoardResponseDto> readBoardList() {
        List<Board> boardList = boardRepository.findAllByOrderByModifiedAtAsc();
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : boardList) {
            if (board.isDelete()) {
                continue;
            }
            BoardResponseDto boardResponseDto = new BoardResponseDto(board);
            boardResponseDtoList.add(boardResponseDto);
        }
        return boardResponseDtoList;
    }

    public BoardResponseDto readBoard(Long boardId) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        if (board.get().isDelete()) {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        BoardResponseDto boardResponseDto = new BoardResponseDto(board.get());
        return boardResponseDto;
    }

    @Transactional
    public BoardResponseDto patchBoard(Long boardId, BoardRequestDto boardRequestDto) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        if (board.get().isDelete()) {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        board.get().BoardPatch(boardRequestDto);
        BoardResponseDto boardResponseDto = new BoardResponseDto(board.get());
        return boardResponseDto;
    }

    @Transactional
    public ResponseDto deleteBoard(Long boardId) {
        Optional<Board> board = boardRepository.findByBoardId(boardId);
        if (board.get().isDelete() == false) {
            board.get().BoardDelete();
        } else {
            throw new IllegalArgumentException("이미 삭제된 게시물입니다.");
        }
        board.get().BoardDelete();
        ResponseDto responseDto = new ResponseDto();
        return responseDto;
    }

}
