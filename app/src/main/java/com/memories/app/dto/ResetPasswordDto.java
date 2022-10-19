package com.memories.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResetPasswordDto extends GenericDto {
	
	private ForgetPasswordTokenDto token;
	private String newPassword;

}
