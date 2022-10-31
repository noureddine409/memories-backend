package com.memories.app.dto;

import javax.validation.constraints.NotBlank;

import com.memories.app.validator.ValidPassword;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ResetPwdDto extends GenericDto {
	@NotBlank
	private String oldPassword;
	@ValidPassword @NotBlank
	private String newPassword;
}
