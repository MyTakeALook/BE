package com.takealook.takealook.dto;

import lombok.Getter;

import javax.persistence.Column;

@Getter
public class BoardRequestDto {
    private String title;
    private String imageUrl;
    private String catName;
    private Integer age;
    private String gender;
    private String text;
}
