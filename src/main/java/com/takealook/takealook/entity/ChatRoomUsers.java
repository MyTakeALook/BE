package com.takealook.takealook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomUsers {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long chatRoomId;
    private long chatRoomUsers = 1L;
    private long chatRoomUsersId = 1L;
    @ManyToOne
    private ChatRoom chatRoom;
    @ManyToOne
    private User roomUser;

    public ChatRoomUsers(ChatRoom chatRoom, User roomUser) {
        this.chatRoom = chatRoom;
        this.roomUser = roomUser;
    }
}
