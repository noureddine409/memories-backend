package com.memories.app.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import static com.memories.app.commun.CoreConstant.Validation.PASSWORD_SIZE_MAX;
import static com.memories.app.commun.CoreConstant.Validation.PASSWORD_SIZE_MIN;

@Data
@NoArgsConstructor
public class UserLoginDto {
	@Email
    @NotBlank
    private String email;
	
	@NotBlank @Length(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX)
    private String password;
}
