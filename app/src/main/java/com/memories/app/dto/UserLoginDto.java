package com.memories.app.dto;



import static com.memories.app.commun.CoreConstant.Validation.PASSWORD_SIZE_MAX;
import static com.memories.app.commun.CoreConstant.Validation.PASSWORD_SIZE_MIN;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserLoginDto {
	@Email @NotBlank
    private String email;
	
	@NotBlank @Length(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX)
    private String password;
}
