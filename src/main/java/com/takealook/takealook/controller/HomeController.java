package com.takealook.takealook.controller;

import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.dto.BoardResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.dto.TestRequest;
import com.takealook.takealook.entity.Board;
import com.takealook.takealook.security.UserDetailsImpl;
import com.takealook.takealook.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
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
    // MediaType 두개 -> 하나만 -> 아예 빼보기 -> RequestPart에 value값 지정해보기

    //@ResponseBody
    @PostMapping(value = "/index/submit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE) // consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    public BoardResponseDto boardCreate(BoardRequestDto boardRequestDto,
                           @RequestPart(value="file", required=false) MultipartFile file,
                            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) throws IOException {

        BoardResponseDto boardResponseDto = boardService.createBoard(boardRequestDto, file, userDetailsImpl);
        return boardResponseDto;
    }

    @RequestMapping(value="WriteBoard.do", method=RequestMethod.POST)
    public void WriteBoard (@RequestParam(value="file", required=false) MultipartFile[] files){
        String FileNames ="";
        System.out.println("Multifile files 확인하려고 작성");

        for (MultipartFile mf : files) {

            String originFileName = mf.getOriginalFilename(); // 원본 파일 명
            long fileSize = mf.getSize(); // 파일 사이즈

            System.out.println("멀티파일 파일즈 네임 : " + originFileName);
            System.out.println("멀티파일 파일즈 사이즈 : " + fileSize);
        }
    }


    @GetMapping("/index")
    public Map<String, List<BoardResponseDto>> boardReadList(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        List<BoardResponseDto> boardResponseDtoList = boardService.readBoardList(userDetailsImpl);
        List<BoardResponseDto> boardResponseRankDtoList = boardService.readBoardRankList(userDetailsImpl);

        Map<String, List<BoardResponseDto>> data = new HashMap<>();
        data.put("rankBoard", boardResponseRankDtoList);
        data.put("boardList", boardResponseDtoList);
        return data;
    }

    @GetMapping("/index/boardList") // boards 권장 boardList // board-list, board-lists, boards, /boards, /boards/..., /boards?
    public List<BoardResponseDto> readBoardList(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        return boardService.readBoardList(userDetailsImpl);
    }
    @GetMapping("/index/rankBoard") // index X
    public List<BoardResponseDto> readBoardRank(@AuthenticationPrincipal UserDetailsImpl userDetailsImpl){
        return boardService.readBoardRankList(userDetailsImpl);
    }

    @GetMapping("/index/detail/{boardId}") // boards/{boardId} url쓸떄 _ -> -
    public BoardResponseDto boardRead(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        System.out.println("여기로오나???");
        BoardResponseDto boardResponseDto = boardService.readBoard(boardId, userDetailsImpl);
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
