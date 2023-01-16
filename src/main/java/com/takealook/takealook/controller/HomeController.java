package com.takealook.takealook.controller;

import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.dto.BoardResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.security.UserDetailsImpl;
import com.takealook.takealook.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @PostMapping("/upload")
    public String fileUpload(@RequestParam("file") MultipartFile file) throws IOException {
        String fullPath = "/C:\\Users\\USER\\Desktop\\hanghae99\\springmvc\\BE\\src\\main\\resources\\static\\img/" + file.getOriginalFilename();
        //String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        file.transferTo(new File(fullPath));

        return "redirect:/";
    }

    @PostMapping("/index/submit") // -> 공통된거 requestMapping
    public BoardResponseDto boardCreate(@RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto, userDetailsImpl);
        return boardResponseDto;
    }

    @GetMapping("/index")
    public Map<String, List<BoardResponseDto>> boardReadList() {
        List<BoardResponseDto> boardResponseRankDtoList = boardService.readBoardRankList();
        List<BoardResponseDto> boardResponseDtoList = boardService.readBoardList();

        Map<String, List<BoardResponseDto>> data = new HashMap<>();
        data.put("boardList", boardResponseDtoList);
        data.put("rankBoard", boardResponseRankDtoList);
        return data;
    }

    @GetMapping("/index/detail/{boardId}")
    public BoardResponseDto boardRead(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.readBoard(boardId);
        return boardResponseDto;
    }

    @PatchMapping("/index/detail/{boardId}")
    public BoardResponseDto boardPatch(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        BoardResponseDto boardResponseDto = boardService.patchBoard(boardId, boardRequestDto, userDetailsImpl);
        return boardResponseDto;
    }

    @DeleteMapping("/index/detail/{boardId}")
    public ResponseDto boardDelete(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        ResponseDto responseDto = boardService.deleteBoard(boardId, userDetailsImpl);
        return responseDto;
    }

    @PostMapping("/board/love/{boardId}")
    public ResponseDto boardLike(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        ResponseDto responseDto = boardService.likeBoard(boardId, userDetailsImpl);
        return responseDto;
    }

}
