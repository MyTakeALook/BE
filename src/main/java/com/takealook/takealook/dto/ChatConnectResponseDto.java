package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ChatConnectResponseDto {
    private Long participantNumber;

    public ChatConnectResponseDto(Long participantNumber) {
        this.participantNumber = participantNumber;
    }
}
