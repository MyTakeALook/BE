package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PasswordFindRequestDto {
    private String username;
    private String email;
    private String password;
}
