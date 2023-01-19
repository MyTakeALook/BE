package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class AuthMessage {
    private String msg;

    public AuthMessage(String msg){
        this.msg = msg;
    }
}
