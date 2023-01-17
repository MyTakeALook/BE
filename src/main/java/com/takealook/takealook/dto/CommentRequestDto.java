package com.takealook.takealook.dto;

import lombok.Getter;

@Getter
public class CommentRequestDto { //
    private long commentId;
    private String comment;

    public CommentRequestDto(String comment) { // form데이터 받기 위해서 생성자 추가해줌
        this.comment = comment;
    }
}
