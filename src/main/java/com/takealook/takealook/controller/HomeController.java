package com.takealook.takealook.controller;

import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.dto.BoardResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.dto.TestRequest;
import com.takealook.takealook.entity.Board;
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
    /*@PostMapping("/upload")
    public String fileUpload(BoardRequestDto boardRequestDto) throws IOException {
        String fullPath = "/C:\\Users\\USER\\Desktop\\hanghae99\\springmvc\\BE\\src\\main\\resources\\static\\img/" + boardRequestDto.getUrlimage().getOriginalFilename();
        //String ext = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".") + 1);
        boardRequestDto.getUrlimage().transferTo(new File(fullPath));

        System.out.println(boardRequestDto.toString());

        return "redirect:/";
    }*/

    @PostMapping("/index/submit") // form 형식으로 받을 경우 @RequestBody 빼줘야하는거 같음
    public BoardResponseDto boardCreate(@RequestBody BoardRequestDto boardRequestDto, @RequestParam(value = "imageurl", required = false) MultipartFile urlimage, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        System.out.println("BoardRequestDto확인");
        System.out.println(boardRequestDto.getText());
        System.out.println(boardRequestDto.getAge());
        System.out.println(boardRequestDto.getCatName());
        System.out.println("BoardRequestDto확인");

        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto, urlimage, userDetailsImpl);
        return boardResponseDto;
    }

    @GetMapping("/index")
    public Map<String, List<BoardResponseDto>> boardReadList() {
        List<BoardResponseDto> boardResponseDtoList = boardService.readBoardList();
        List<BoardResponseDto> boardResponseRankDtoList = boardService.readBoardRankList();

        Map<String, List<BoardResponseDto>> data = new HashMap<>();
        data.put("rankBoard", boardResponseRankDtoList);
        data.put("boardList", boardResponseDtoList);
        return data;
    }

    @GetMapping("/index/boardList")
    public List<BoardResponseDto> readBoardList() {
        return boardService.readBoardList();
    }
    @GetMapping("/index/rankBoard")
    public List<BoardResponseDto> readBoardRank(){
        return boardService.readBoardRankList();
    }

    @GetMapping("/index/detail/{boardId}")
    public BoardResponseDto boardRead(@PathVariable Long boardId) {
        BoardResponseDto boardResponseDto = boardService.readBoard(boardId);
        return boardResponseDto;
    }

    @PatchMapping("/index/detail/{boardId}")
    public BoardResponseDto boardPatch(@PathVariable Long boardId, @RequestBody BoardRequestDto boardRequestDto, @RequestParam(value = "imageurl", required = false) MultipartFile urlimage, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {
        BoardResponseDto boardResponseDto = boardService.patchBoard(boardId, boardRequestDto, urlimage, userDetailsImpl);
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
