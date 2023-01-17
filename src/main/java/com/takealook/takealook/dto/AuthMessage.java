package com.takealook.takealook.dto;

import lombok.Getter;

@Getter
public class AuthMessage {
    private String msg;

    public AuthMessage(String msg){
        this.msg = msg;
    }
}
