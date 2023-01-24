package com.takealook.takealook.dto;

import com.takealook.takealook.entity.ChatRoomMessage;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ChatRoomMessageResponseDto {
    private String sender;
    private String message;

    public ChatRoomMessageResponseDto(String sender, String chatMessage) {
        this.sender = sender;
        this.message = chatMessage;
    }
}
