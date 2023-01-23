package com.takealook.takealook.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
// @Setter 이거 없애니까 message, userJWT roomUUID가 회색빛으로 바뀜
@NoArgsConstructor
public class ChatMessage {
    private String sender;
    private String message; // 메시지
    private String userJWT;
    private String roomUUID;

    public void ChatSender(String sender) {
        this.sender = sender;
    }
}
