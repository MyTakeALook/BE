package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailRequestDto {
    private String sender;  //보내는사람
    private String email;  //받는사람
    private String title;  //제목
    private String cont;  //내용
}
