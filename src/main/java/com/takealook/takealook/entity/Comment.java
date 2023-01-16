package com.takealook.takealook.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.takealook.takealook.dto.CommentRequestDto;
import com.takealook.takealook.security.UserDetailsImpl;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Comment extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long commentId;
    @Column
    private String comment;
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BOARD_ID")
    @JsonIgnore
    private Board board;
    private boolean isDelete = false;

    public Comment(CommentRequestDto commentRequestDto, Board board, User user) {
        this.comment = commentRequestDto.getComment();
        this.board = board;
        this.user = user;
    }

    public void CommentPut(CommentRequestDto commentRequestDto) {
        this.comment = commentRequestDto.getComment();
    }
    public void CommentDelete() {
        this.isDelete = true;
    } //
}
