package com.memories.app.dto;



import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {

    private String email;
    private String password;
}
