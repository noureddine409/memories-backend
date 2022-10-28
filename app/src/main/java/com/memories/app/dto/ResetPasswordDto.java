package com.memories.app.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.memories.app.validator.ValidPassword;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordDto extends GenericDto {
	@Size(min = 64, message = "invalid Token" )
	@Size(max = 64, message = "invalid Token" )
	private ForgetPasswordTokenDto token;
	@NotBlank @ValidPassword
	private String newPassword;

}
