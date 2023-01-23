package com.takealook.takealook.controller;

import com.takealook.takealook.chat.ChatConnect;
import com.takealook.takealook.chat.ChatDisconnect;
import com.takealook.takealook.chat.ChatMessage;
import com.takealook.takealook.dto.ChatConnectResponseDto;
import com.takealook.takealook.dto.ChatDisconnectResponseDto;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.repository.ChatRoomRepository;
import com.takealook.takealook.security.UserDetailsImpl;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class ChatController {
    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatRoomRepository chatRoomRepository;
    private final com.takealook.takealook.jwt.ChatJwtUtil chatjwtUtil;
    @ResponseBody
    @MessageMapping("/chat/message") // socket 통신은 request를 안주나???
    public void message(ChatMessage message) {
        String token = chatjwtUtil.resolveToken(message.getUserJWT());

        if(token != null) {
            if(!chatjwtUtil.validateToken(token)) {
                System.out.println("토큰 에러!");
                return;
            }
        }
        Claims info = chatjwtUtil.getUserInfoFromToken(token);
        System.out.println(info);
        System.out.println(info.getSubject());
        message.ChatSender(info.getSubject());
        messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomUUID(), message); // + roomId 있었음
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

//    @MessageMapping("/chat/disconnect")
//    public void disconnect(ChatDisconnect chatDisconnect) {
//        Optional<ChatRoom> chatRoom = chatRoomRepository.findByParticipantSession(chatDisconnect.getSenderId());
//
//        chatRoomRepository.delete(chatRoom.get());
//
//        ChatDisconnectResponseDto chatDisconnectResponseDto = new ChatDisconnectResponseDto(Long.valueOf(chatRoomRepository.findAll().size()));
//        messagingTemplate.convertAndSend("/sub/chat/room", chatDisconnectResponseDto); // + roomId 있었음
//    }
}
