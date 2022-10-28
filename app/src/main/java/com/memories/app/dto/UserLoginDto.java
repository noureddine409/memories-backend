package com.memories.app.dto;



import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {
	@Email @NotBlank
    private String email;
	
	@NotBlank
    private String password;
}
