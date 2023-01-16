package com.takealook.takealook.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Liked {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;
    @ManyToOne
    private User user;
    @ManyToOne
    private Board board;
    public Liked(User user, Board board) {
        this.user = user;
        this.board = board;
    }
}
