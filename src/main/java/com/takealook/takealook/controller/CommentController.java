package com.takealook.takealook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.takealook.takealook.dto.CommentRequestDto;
import com.takealook.takealook.dto.CommentResponseDto;
import com.takealook.takealook.dto.ResponseDto;
import com.takealook.takealook.security.UserDetailsImpl;
import com.takealook.takealook.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        //System.out.println(request.getReader().lines().getClass());
        System.out.println(commentRequestDto.getComment()); // null
        //System.out.println(request.getReader().readLine());
        //System.out.println(userDetailsImpl.getUser().getUsername());
        System.out.println("1111111111111111111111111111111111");

//        public class ObjectMapperEx {
//            public static void main(String[] args) {
//                ObjectMapper objectMapper = new ObjectMapper();
//
//                // Java Object ->  JSON
//                Person person = new Person("zooneon", 25, "seoul");
//                try {
//                    objectMapper.writeValue(new File("src/person.json"), person);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//
//        private void getSummary (String searchDate) {
//            try {
//                ResponseObject responseObject = resourceClient.getForResponseObject();
//
//                ObjectMapper om = new ObjectMapper();
//                Summary summary = om.convertValue(responseObject.getData(), new TypeReference<Summary>() {});
//
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        }

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
