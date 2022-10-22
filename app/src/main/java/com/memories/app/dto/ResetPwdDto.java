package com.memories.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResetPwdDto extends GenericDto {
	
	private String oldPassword;
	private String newPassword;
}
