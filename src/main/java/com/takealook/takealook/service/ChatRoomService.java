package com.takealook.takealook.service;

import com.takealook.takealook.chat.ChatMessage;
import com.takealook.takealook.dto.ChatRoomMessageResponseDto;
import com.takealook.takealook.dto.ChatRoomResponseDto;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.entity.ChatRoomMessage;
import com.takealook.takealook.entity.ChatRoomUsers;
import com.takealook.takealook.entity.User;
import com.takealook.takealook.repository.ChatRoomMessageRepository;
import com.takealook.takealook.repository.ChatRoomRepository;
import com.takealook.takealook.repository.ChatRoomUsersRepository;
import com.takealook.takealook.repository.UserRepository;
import com.takealook.takealook.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

// import static java.awt.Color.red; // 이런것도 있네 근데 이거 뭐임??

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final UserRepository userRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUsersRepository chatRoomUsersRepository;
    private final ChatRoomMessageRepository chatRoomMessageRepository;
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
    @Transactional
    public List<ChatRoomMessageResponseDto> enterChatRoom(String roomUUID, UserDetailsImpl userDetailsImpl) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomUUID(roomUUID);

        ChatRoomUsers chatRoomUsers = new ChatRoomUsers(chatRoom.get(), userDetailsImpl.getUser());
        chatRoomUsersRepository.save(chatRoomUsers);

        List<ChatRoomMessage> chatRoomMessages = chatRoomMessageRepository.findAllByChatRoom(chatRoom.get());

        List<ChatRoomMessageResponseDto> chatRoomMessageResponseDtos = new ArrayList<>();
        for (ChatRoomMessage chatRoomMessage : chatRoomMessages) {
            ChatRoomMessageResponseDto chatRoomMessageResponseDto = new ChatRoomMessageResponseDto(chatRoomMessage.getSender().getUsername(), chatRoomMessage.getChatMessage());
            chatRoomMessageResponseDtos.add(chatRoomMessageResponseDto);
        }
        return chatRoomMessageResponseDtos;
    }
    @Transactional
    public void exitChatRoom(String roomUUID, UserDetailsImpl userDetailsImpl) {
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomUUID(roomUUID);

        Optional<ChatRoomUsers> chatRoomUsers =
                chatRoomUsersRepository.findByChatRoomAndRoomUser(chatRoom.get(), userDetailsImpl.getUser());
        chatRoomUsersRepository.delete(chatRoomUsers.get());
    }
    @Transactional
    public void createChatRoomMessage(ChatMessage chatMessage) {
        Optional<User> user = userRepository.findByUsername(chatMessage.getSender());
        Optional<ChatRoom> chatRoom = chatRoomRepository.findByRoomUUID(chatMessage.getRoomUUID());

        ChatRoomMessage chatRoomMessage = new ChatRoomMessage(user.get(), chatMessage.getMessage(), chatRoom.get());
        chatRoomMessageRepository.save(chatRoomMessage);
    }
}
