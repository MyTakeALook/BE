package com.takealook.takealook.repository;

import com.takealook.takealook.entity.Board;
import com.takealook.takealook.entity.ChatRoom;
import com.takealook.takealook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    //Optional<ChatRoom> findByParticipantSession(String participantSession);
    Optional<ChatRoom> findByRoomUUID(String roomUUID);
}
