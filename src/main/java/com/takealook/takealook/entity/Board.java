package com.takealook.takealook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takealook.takealook.dto.BoardRequestDto;
import com.takealook.takealook.entity.Liked;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter // Setter를 쓰면 회사한테 혼남
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
    @ManyToOne
    private User user;
    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE)
    @JsonIgnore
    private List<Comment> comment = new ArrayList<>();
    private boolean isDelete = false;
    @Column
    private Long visit = 0L;

    public Board(BoardRequestDto boardRequestDto, String imageurl, User user) {
        this.imageurl = imageurl;
        this.title = boardRequestDto.getTitle();
        this.catName = boardRequestDto.getCatName();
        this.age = boardRequestDto.getAge();
        this.gender = boardRequestDto.getGender();
        this.text = boardRequestDto.getText();
        this.user = user;
    }
    public void BoardPatch(BoardRequestDto boardRequestDto, String imageurl) {
        this.title = (boardRequestDto.getTitle() == null) ? this.getTitle() : boardRequestDto.getTitle();
        this.imageurl = (imageurl == null) ? this.getImageurl() : imageurl;
        this.catName = (boardRequestDto.getCatName() == null) ? this.getCatName() : boardRequestDto.getCatName();
        this.age = (boardRequestDto.getAge() == null) ? this.getAge() : boardRequestDto.getAge();
        this.gender = (boardRequestDto.getGender() == null) ? this.getGender() : boardRequestDto.getGender();
        this.text = (boardRequestDto.getText() == null) ? this.getText() : boardRequestDto.getText();
    }
    public void BoardPatchNoImage(BoardRequestDto boardRequestDto) {
        this.title = (boardRequestDto.getTitle() == null) ? this.getTitle() : boardRequestDto.getTitle();
        this.imageurl = this.getImageurl();
        this.catName = (boardRequestDto.getCatName() == null) ? this.getCatName() : boardRequestDto.getCatName();
        this.age = (boardRequestDto.getAge() == null) ? this.getAge() : boardRequestDto.getAge();
        this.gender = (boardRequestDto.getGender() == null) ? this.getGender() : boardRequestDto.getGender();
        this.text = (boardRequestDto.getText() == null) ? this.getText() : boardRequestDto.getText();
    }
    public void BoardDelete() {
        this.isDelete = true;
    }
    public void BoardVisit() {
        this.visit += 1L;
    }
}
