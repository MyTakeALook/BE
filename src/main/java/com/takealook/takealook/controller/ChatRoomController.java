package com.takealook.takealook.controller;

import com.takealook.takealook.dto.ChatRoomResponseDto;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.security.UserDetailsImpl;
import com.takealook.takealook.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller // @ResponseBody 필요할때 쓰기!
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/chatroom")
    public String chatroom(Model model) {
        String senderId = chatRoomService.createSenderId();
        model.addAttribute("senderId", senderId);
        return "Chat";
    }

    @GetMapping("/chat/room/{roomUUID}")
    public String chatRoom(Model model) {
        return "ChatRoom";
    }

    @GetMapping("/chatrooms")
    public String chatRooms(Model model) {
        return "ChatRoomList";
    }
    @ResponseBody
    @GetMapping("/chatrooms-list")
    public List<ChatRoomResponseDto> chatRoomsList() {
        List<ChatRoomResponseDto> chatRoomResponseDtos = chatRoomService.readChatRooms();
        return chatRoomResponseDtos;
    }
    @ResponseBody
    @PostMapping("/chatroom")
    public ChatRoomResponseDto chatroomCreate(String roomTitle,
            @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        ChatRoomResponseDto chatRoomResponseDto = chatRoomService.createChatRoom(roomTitle, userDetailsImpl);
        return chatRoomResponseDto;
    }


}
