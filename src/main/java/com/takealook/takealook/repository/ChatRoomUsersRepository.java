package com.takealook.takealook.repository;

import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.entity.ChatRoomMessage;
import com.takealook.takealook.entity.ChatRoomUsers;
import com.takealook.takealook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomUsersRepository extends JpaRepository<ChatRoomUsers, Long> {
    List<ChatRoomUsers> findAllByChatRoom(ChatRoom chatRoom);
    Optional<ChatRoomUsers> findByChatRoomAndRoomUser(ChatRoom chatRoom, User user);
}