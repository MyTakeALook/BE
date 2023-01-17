package com.takealook.takealook.dto;

import lombok.Getter;

@Getter
public class LoginErrorMessage {
    private Integer status;
    private String msg;

    public LoginErrorMessage(String msg) {
        this.msg = msg;
    }

    public LoginErrorMessage(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }
}
