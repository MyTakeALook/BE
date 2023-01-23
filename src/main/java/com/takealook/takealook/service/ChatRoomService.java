package com.takealook.takealook.service;

import com.takealook.takealook.dto.ChatRoomResponseDto;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.repository.ChatRoomRepository;
import com.takealook.takealook.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// import static java.awt.Color.red; // 이런것도 있네 근데 이거 뭐임??

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    @Transactional // 이거를 빼면 데이터 생성이 안되나????
    public String createSenderId() {
        String senderId = UUID.randomUUID().toString();
        return senderId;
    }

    public List<ChatRoomResponseDto> readChatRooms() {
        List<ChatRoom> chatRooms = chatRoomRepository.findAll();
        List<ChatRoomResponseDto> chatRoomResponseDtos = new ArrayList<>();
        for (ChatRoom chatRoom : chatRooms) {
            ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom);
            chatRoomResponseDtos.add(chatRoomResponseDto);
        }
        return chatRoomResponseDtos;
    }
    @Transactional
    public ChatRoomResponseDto createChatRoom(String roomTitle, UserDetailsImpl userDetailsImpl) {
        String roomUUID = UUID.randomUUID().toString();
        ChatRoom chatRoom = new ChatRoom(roomTitle, userDetailsImpl.getUser(), roomUUID);
        chatRoomRepository.save(chatRoom);

        ChatRoomResponseDto chatRoomResponseDto = new ChatRoomResponseDto(chatRoom);
        return chatRoomResponseDto;
    }
}
