package com.takealook.takealook.controller;

import com.takealook.takealook.chat.ChatConnect;
import com.takealook.takealook.chat.ChatDisconnect;
import com.takealook.takealook.chat.ChatMessage;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    @MessageMapping("/chat/message")
    public void message(ChatMessage message) {
        System.out.println("메세지맵핑!");
        /*if (ChatMessage.MessageType.ENTER.equals(message.getType()))
            message.setMessage(message.getSender() + "님이 입장하셨습니다.");*/
        //chatService.createChatMsg(message.getRoomId(), message.getMessage(), message.getSender());
        System.out.println(message.getMessage());
        System.out.println(message.getSenderId());
        System.out.println(message.getSender());
        messagingTemplate.convertAndSend("/sub/chat/room", message); // + roomId 있었음
    }

    @MessageMapping("/chat/connect")
    public void connect(ChatConnect chatConnect) {
        System.out.println("입장 감지!");
        System.out.println(chatConnect.getSenderId());

        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(1L);
        chatRoom.get().ParticipantNumberUp();
        chatRoomRepository.save(chatRoom.get());

        messagingTemplate.convertAndSend("/sub/chat/room", chatRoom.get()); // + roomId 있었음
    }

    @MessageMapping("/chat/disconnect")
    public void disconnect(ChatDisconnect chatDisconnect) {
        System.out.println("퇴장 감지!");
        System.out.println(chatDisconnect.getSenderId());

        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(1L);
        //chatRoom.get().ParticipantNumberDown();
        //System.out.println(chatRoom.get().getParticipantNumber());
        //chatRoomRepository.save(chatRoom.get());

        messagingTemplate.convertAndSend("/sub/chat/room", chatRoom.get()); // + roomId 있었음
    }

    @GetMapping("/chat/message")
    public void test() {
        System.out.println("혹시 여기는 오너ㅏ????");
    }
}
