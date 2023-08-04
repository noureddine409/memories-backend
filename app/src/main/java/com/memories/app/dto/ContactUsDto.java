package com.memories.app.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class ContactUsDto extends GenericDto {
	@NotBlank
	private String subject;
	@NotBlank
	private String body;
}
