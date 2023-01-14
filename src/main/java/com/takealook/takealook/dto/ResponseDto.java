package com.takealook.takealook.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private boolean result;

    public ResponseDto() {
        this.result = true;
    }
}
