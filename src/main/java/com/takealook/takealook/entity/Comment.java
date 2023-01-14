package com.takealook.takealook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takealook.takealook.dto.CommentRequestDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@RequiredArgsConstructor
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    @Column
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @JsonIgnore
    private Board board;
    private boolean isDelete = false;

    public Comment(CommentRequestDto commentRequestDto, Board board) {
        this.comment = commentRequestDto.getComment();
        this.board = board;
    }

    public void CommentPut(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
    public void CommentDelete() {
        this.isDelete = true;
    } //
}
