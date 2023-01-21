package com.takealook.takealook.service;

import com.takealook.takealook.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

// import static java.awt.Color.red; // 이런것도 있네 근데 이거 뭐임??

@Service
public class ChatRoomService {
    @Transactional // 이거를 빼면 데이터 생성이 안되나????
    public String createSenderId() {
        String senderId = UUID.randomUUID().toString();
        return senderId;
    }

    public void message2() {

        //messagingTemplate.convertAndSend("/sub/chat/room", "수신 테슨트!");
    }
}
