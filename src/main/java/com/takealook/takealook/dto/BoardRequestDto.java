package com.takealook.takealook.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;


@Getter
public class BoardRequestDto {
    private String title;
    private String catName;
    private Integer age;
    private String gender;
    private String text;

    public BoardRequestDto(String title, String catName, Integer age, String gender, String text) {
        this.title = title;
        this.catName = catName;
        this.age = age;
        this.gender = gender;
        this.text = text;
    }

    @Override
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                "catName='" + catName + '\'' +
                "age='" + age + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
