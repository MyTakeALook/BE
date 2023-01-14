package com.takealook.takealook.dto;

import lombok.Getter;

@Getter
public class ResponseDto {
    private boolean result;

    //public ResponseDto() {
        //this.result = true;
    //}

    public void ResponseTrue() {
        this.result = true;
    }

    public void ResponseFalse() {
        this.result = false;
    }
}
