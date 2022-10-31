package com.memories.app.dto;

import com.memories.app.validator.ValidForgetPasswordToken;
import com.memories.app.validator.ValidPassword;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class ResetPasswordDto extends GenericDto {
	@ValidForgetPasswordToken
	private ForgetPasswordTokenDto token;
	@ValidPassword
	private String newPassword;
	
	
	

}
