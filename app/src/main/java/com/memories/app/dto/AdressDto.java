package com.memories.app.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import static com.memories.app.commun.CoreConstant.Validation.POSTAL_CODE_REGEX;
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdressDto extends GenericDto {
	private String country;
	private String city;
	@Pattern(regexp = POSTAL_CODE_REGEX)
	private String postalCode;
}
