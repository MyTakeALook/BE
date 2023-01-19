package com.takealook.takealook.dto;

import com.takealook.takealook.entity.Board;
import com.takealook.takealook.entity.Comment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class BoardResponseDto { //
    private long boardId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;
    private String imageUrl;
    private String catName;
    private Integer age;
    private String gender;
    private String text;
    private List<CommentResponseDto> commentResponseDtoList = new ArrayList<>();
    private Long love;
    private Long visit;


    public BoardResponseDto(Board board, Long likedSize) {
        this.boardId = board.getBoardId();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.title = board.getTitle();
        this.imageUrl = board.getImageurl();
        this.catName = board.getCatName();
        this.age = board.getAge();
        this.gender = board.getGender();
        this.text = board.getText();
        this.love = likedSize;
        this.visit = board.getVisit();
        for (Comment comment : board.getComment()) {
            if (comment.isDelete()) {
                continue;
            }
            CommentResponseDto commentResponseDto = new CommentResponseDto(comment);
            this.commentResponseDtoList.add(commentResponseDto);
        }
    }
}
