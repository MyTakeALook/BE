package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommentRequestDto { //
    private String comment;
    public CommentRequestDto(String comment) { // form데이터 받기 위해서 생성자 추가해줌
        this.comment = comment;
    }
}
