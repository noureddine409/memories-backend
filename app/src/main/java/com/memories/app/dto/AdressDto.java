package com.memories.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class AdressDto extends GenericDto {
	private String country;
	private String city;
	private int postalCode;
}
