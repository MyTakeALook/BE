package com.takealook.takealook.controller;

import com.takealook.takealook.dto.ChatRoomMessageResponseDto;
import com.takealook.takealook.dto.ChatRoomResponseDto;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.security.UserDetailsImpl;
import com.takealook.takealook.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
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

    @ResponseBody
    @PostMapping("/chat/room/enter/{roomUUID}")
    public List<ChatRoomMessageResponseDto> chatRoomEnter(String roomUUID,
                         @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        List<ChatRoomMessageResponseDto> chatRoomMessageResponseDtos = chatRoomService.enterChatRoom(roomUUID, userDetailsImpl);
        return chatRoomMessageResponseDtos;
    }

    @ResponseBody
    @PostMapping("/chat/room/exit/{roomUUID}")
    public void chatRoomExit(String roomUUID,
                              @AuthenticationPrincipal UserDetailsImpl userDetailsImpl) {
        chatRoomService.exitChatRoom(roomUUID, userDetailsImpl);
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
