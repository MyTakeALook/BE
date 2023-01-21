package com.takealook.takealook.controller;

import com.takealook.takealook.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // @ResponseBody 필요할때 쓰기!
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;
    @GetMapping("/chatroom")
    public String chatroom(Model model) {
        String senderId = chatRoomService.createSenderId();
        model.addAttribute("senderId", senderId);
        System.out.println("여기는 오나요 챗룸 컨트롤러!!");
        return "Chat";
    }
}
