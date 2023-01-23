package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatDisconnectResponseDto {
    private Long participantNumber;

    public ChatDisconnectResponseDto(Long participantNumber) {
        this.participantNumber = participantNumber;
    }
}
