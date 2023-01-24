package com.takealook.takealook.repository;

import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.entity.ChatRoomMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomMessageRepository extends JpaRepository<ChatRoomMessage, Long> {
    List<ChatRoomMessage> findAllByChatRoom(ChatRoom chatRoom);
}
