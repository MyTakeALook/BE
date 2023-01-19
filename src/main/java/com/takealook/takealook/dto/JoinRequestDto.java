package com.takealook.takealook.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
public class JoinRequestDto {

    //@Size(min=4,max=10)
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]")   //  (?=.*[a-z])
    private String username;

    //@Size(min = 8, max = 15)
    //@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]")
    private String password;
}

