package com.takealook.takealook.controller;

import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.dto.BoardResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.security.UserDetailsImpl;
import com.takealook.takealook.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
@RestController
@RequiredArgsConstructor
public class HomeController {
    private final BoardService boardService;
    @GetMapping("/")
    public ModelAndView home() {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index"); //뷰의 이름
        return mv; // view + data passvariable
    }
    @PostMapping("/index/submit") // -> 공통된거 requestMapping
    public BoardResponseDto boardCreate(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        System.out.println(userDetailsImpl);
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto);
        return boardResponseDto;
    }

    @GetMapping("/index")
    public List<BoardResponseDto> boardReadList() {
        List<BoardResponseDto> boardResponseDtoList = boardService.readBoardList();
        return boardResponseDtoList;
    }

    @GetMapping("/index/detail/{boardId}")
    public BoardResponseDto boardRead(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.readBoard(boardId);
        return boardResponseDto;
    }

    @PatchMapping("/index/detail/{boardId}")
    public BoardResponseDto boardPatch(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto) {
        BoardResponseDto boardResponseDto = boardService.patchBoard(boardId, boardRequestDto);
        return boardResponseDto;
    }

    @DeleteMapping("/index/detail/{boardId}")
    public ResponseDto boardDelete(@PathVariable Long boardId) {
        ResponseDto responseDto = boardService.deleteBoard(boardId);
        return responseDto;
    }

//    @PostMapping("/board/love/{boardId}") // -> 공통된거 requestMapping
//    public ResponseDto boardLove(@PathVariable Long boardId) {
//        ResponseDto responseDto = boardService.loveBoard(boardId);
//        return responseDto;
//    }

}
