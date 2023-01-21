package com.takealook.takealook.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoom {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long chatRoomId;
    private Long participantNumber = 0L;

    public void ParticipantNumberUp() {
        this.participantNumber += 1L;
    }
    public void ParticipantNumberDown() {
        this.participantNumber -= 1L;
    }
}
