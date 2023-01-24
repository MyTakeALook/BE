package com.takealook.takealook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomMessage {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long chatRoomMessageId;
    @ManyToOne
    private User sender;
    private String chatMessage;
    @ManyToOne
    private ChatRoom chatRoom;

    public ChatRoomMessage(User sender, String chatMessage, ChatRoom chatRoom) {
        this.sender = sender;
        this.chatMessage = chatMessage;
        this.chatRoom = chatRoom;
    }
}
