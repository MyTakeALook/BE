package com.takealook.takealook.dto;

import com.takealook.takealook.entity.Board;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class BoardResponseDto {
    private long id;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private String title;
    private String imageUrl;
    private String catName;
    private Integer age;
    private String gender;
    private String text;

    public BoardResponseDto(Board board) {
        this.id = board.getBoardId();
        this.createdAt = board.getCreatedAt();
        this.modifiedAt = board.getModifiedAt();
        this.title = board.getTitle();
        this.imageUrl = board.getImageurl();
        this.catName = board.getCatName();
        this.age = board.getAge();
        this.gender = board.getGender();
        this.text = board.getText();
    }
}
