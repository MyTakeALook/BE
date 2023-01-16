package com.takealook.takealook.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
public class BoardRequestDto {
    private String title;
    private MultipartFile urlimage;
    private String catName;
    private Integer age;
    private String gender;
    private String text;
}
