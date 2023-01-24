package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginErrorMessage {
    private Integer status;
    private String msg;
    private String userName;
    private String accessToken;

    public LoginErrorMessage(String msg) {
        this.msg = msg;
    }

    public LoginErrorMessage(Integer status, String msg, String userName, String accessToken) {
        this.status = status;
        this.msg = msg;
        this.userName = userName;
        this.accessToken = accessToken;
    }
}
