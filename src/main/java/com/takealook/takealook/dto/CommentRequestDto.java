package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto { //
    private String comment;

    public CommentRequestDto() {} // 빈생성자를 넣어줘야 받을 수 있음
    public CommentRequestDto(String comment) { // form데이터 받기 위해서 생성자 추가해줌
        this.comment = comment;
    }
}
