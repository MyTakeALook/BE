package com.takealook.takealook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.entity.Liked;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@RequiredArgsConstructor
public class Board extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long boardId;
    @Column
    private String imageurl;
    @Column
    private String title;
    @Column
    private String catName;
    @Column
    private Integer age;
    @Column
    private String gender;
    @Column
    private String text;
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comment = new ArrayList<>();
    @ManyToOne
    private Liked liked;
    private boolean isDelete = false; //

    public Board(BoardRequestDto boardRequestDto) {
        this.imageurl = boardRequestDto.getImageUrl();
        this.title = boardRequestDto.getTitle();
        this.catName = boardRequestDto.getCatName();
        this.age = boardRequestDto.getAge();
        this.gender = boardRequestDto.getGender();
        this.text = boardRequestDto.getText();
    }
    public void BoardPatch(BoardRequestDto boardRequestDto) {
        this.title = (boardRequestDto.getTitle() == null) ? this.getTitle() : boardRequestDto.getTitle();
        this.imageurl = (boardRequestDto.getImageUrl() == null) ? this.getImageurl() : boardRequestDto.getImageUrl();
        this.catName = (boardRequestDto.getCatName() == null) ? this.getCatName() : boardRequestDto.getCatName();
        this.age = (boardRequestDto.getAge() == null) ? this.getAge() : boardRequestDto.getAge();
        this.gender = (boardRequestDto.getGender() == null) ? this.getGender() : boardRequestDto.getGender();
        this.text = (boardRequestDto.getText() == null) ? this.getText() : boardRequestDto.getText();
    }
    public void BoardDelete() {
        this.isDelete = true;
    }
}
