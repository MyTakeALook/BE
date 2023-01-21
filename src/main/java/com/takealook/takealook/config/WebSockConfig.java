package com.takealook.takealook.config;

import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.repository.ChatRoomRepository;
import com.takealook.takealook.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Optional;

@Configuration
@EnableWebSocketMessageBroker
@RequiredArgsConstructor
public class WebSockConfig implements WebSocketMessageBrokerConfigurer {
    private final ChatRoomService chatRoomService;
    private final ChatRoomRepository chatRoomRepository;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        System.out.println("여기로 다시오는건가??????");
        config.enableSimpleBroker("/sub");
        config.setApplicationDestinationPrefixes("/pub");
        //System.out.println(config.getPathMatcher());
    }
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        System.out.println("아님 여기로????????");
        registry.addEndpoint("/ws-stomp").setAllowedOriginPatterns("*").withSockJS();
    }
    @EventListener
    public void connectEvent(SessionConnectEvent sessionConnectEvent){
        System.out.println(sessionConnectEvent);
        System.out.println("연결 성공 감지!_!#!#!#!@#!@@#!@!#!$!@");

        System.out.println("히얼유얄");
        //return "redirect:chat/message";
    }
    @EventListener
    public void onDisconnectEvent(SessionDisconnectEvent sessionDisconnectEvent) {
        System.out.println(sessionDisconnectEvent.getSessionId());
        System.out.println("연결 끊어짐 감지!~!!!!!!!!!!!!!!!!!!!!!!!!");
        System.out.println("이거 됨??");

        Optional<ChatRoom> chatRoom = chatRoomRepository.findById(1L);
        chatRoom.get().ParticipantNumberDown();
        System.out.println(chatRoom.get().getParticipantNumber());
        chatRoomRepository.save(chatRoom.get());
        //chatRoomService.deleteSenderId();
    }

}