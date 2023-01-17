package com.takealook.takealook.dto;

import lombok.Getter;

@Getter
public class PasswordFindRequestDto {
    private String username;
    private String email;
    private String password;
}
