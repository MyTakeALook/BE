package com.takealook.takealook.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessage {
    private String sender; // 메시지 보낸사람
    private String message; // 메시지
    private String senderId;
}
