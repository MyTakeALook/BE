package com.takealook.takealook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long chatRoomId;
    private String roomTitle;
    @ManyToOne
    private User roomOwner;
    private String roomUUID;
    public ChatRoom(String roomTitle, User roomOwner, String roomUUID) {
        this.roomTitle = roomTitle;
        this.roomOwner = roomOwner;
        this.roomUUID = roomUUID;
    }
    //private Long participantNumber = 0L;
    //private String participantSession;


//    public void ParticipantNumberUp() {
//        this.participantNumber += 1L;
//    }
//    public void ParticipantNumberDown() {
//        this.participantNumber -= 1L;
//    }
}
