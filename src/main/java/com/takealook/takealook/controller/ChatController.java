package com.takealook.takealook.controller;

import com.takealook.takealook.chat.ChatConnect;
import com.takealook.takealook.chat.ChatDisconnect;
import com.takealook.takealook.chat.ChatMessage;
import com.takealook.takealook.dto.ChatConnectResponseDto;
import com.takealook.takealook.dto.ChatDisconnectResponseDto;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.entity.ChatRoomUsers;
import com.takealook.takealook.repository.ChatRoomRepository;
import com.takealook.takealook.repository.ChatRoomUsersRepository;
import com.takealook.takealook.service.ChatRoomService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomService chatRoomService;
    private final com.takealook.takealook.jwt.ChatJwtUtil chatjwtUtil;
    private final ChatRoomUsersRepository chatRoomUsersRepository;
    private final ChatRoomRepository chatRoomRepository;
    @ResponseBody
    @MessageMapping("/chat/message") // socket 통신은 request를 안주나???
    public void message(ChatMessage message) {
        String userName = chatResolveToken(message.getUserJWT());
        message.ChatSender(userName);

        chatRoomService.createChatRoomMessage(message);
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomUUID(), message); // + roomId 있었음
    }

    @MessageMapping("/chat/connect")
    public void connect(ChatConnect chatConnect) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomUUID(chatConnect.getRoomUUID());
        List<ChatRoomUsers> chatRoomUsers = chatRoomUsersRepository.findAllByChatRoom(chatRoom.get());

        ChatConnectResponseDto chatConnectResponseDto = new ChatConnectResponseDto(Long.valueOf(chatRoomUsers.size()));
        messagingTemplate.convertAndSend("/sub/chat/room/" + chatConnect.getRoomUUID(), chatConnectResponseDto); // + roomId 있었음
    }

    @MessageMapping("/chat/disconnect")
    public void disconnect(ChatDisconnect chatDisconnect) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomUUID(chatDisconnect.getRoomUUID());
        List<ChatRoomUsers> chatRoomUsers = chatRoomUsersRepository.findAllByChatRoom(chatRoom.get());

        ChatDisconnectResponseDto chatDisconnectResponseDto = new ChatDisconnectResponseDto(Long.valueOf(chatRoomUsers.size()));

        messagingTemplate.convertAndSend("/sub/chat/room/" + chatDisconnect.getRoomUUID(), chatDisconnectResponseDto); // + roomId 있었음
    }

//    @MessageMapping("/chat/connect")
//    public void connect(ChatConnect chatConnect) {
//        ChatRoom chatRoom = new ChatRoom(chatConnect.getSenderId());
//
//        chatRoomRepository.save(chatRoom);
//
//        ChatConnectResponseDto chatConnectResponseDto = new ChatConnectResponseDto(Long.valueOf(chatRoomRepository.findAll().size()));
//        messagingTemplate.convertAndSend("/sub/chat/room", chatConnectResponseDto); // + roomId 있었음
//    }
//
//    @MessageMapping("/chat/disconnect")
//    public void disconnect(ChatDisconnect chatDisconnect) {
//        Optional<ChatRoom> chatRoom = chatRoomRepository.findByParticipantSession(chatDisconnect.getSenderId());
//
//        chatRoomRepository.delete(chatRoom.get());
//
//        ChatDisconnectResponseDto chatDisconnectResponseDto = new ChatDisconnectResponseDto(Long.valueOf(chatRoomRepository.findAll().size()));
//        messagingTemplate.convertAndSend("/sub/chat/room", chatDisconnectResponseDto); // + roomId 있었음
//    }

    public String chatResolveToken(String userJWT) {
        String token = chatjwtUtil.resolveToken(userJWT);

        if(token != null) {
            if(!chatjwtUtil.validateToken(token)) {
                System.out.println("토큰 에러!");
                return "토큰 에러";
            }
        }
        Claims info = chatjwtUtil.getUserInfoFromToken(token);
        return info.getSubject();
    }
}
