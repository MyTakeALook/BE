package com.takealook.takealook.dto;

import com.takealook.takealook.entity.ChatRoom;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatRoomResponseDto {
    private long roomId;
    private String roomTitle;
    private String roomOwner;
    private String roomUUID;

    public ChatRoomResponseDto(ChatRoom chatRoom) {
        this.roomId = chatRoom.getChatRoomId();
        this.roomTitle = chatRoom.getRoomTitle();
        this.roomOwner = chatRoom.getRoomOwner().getUsername();
        this.roomUUID = chatRoom.getRoomUUID();
    }
}
