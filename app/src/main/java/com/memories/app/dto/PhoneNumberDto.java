package com.memories.app.dto;

import com.memories.app.model.GenericEnum.Region;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PhoneNumberDto extends GenericDto{
	private Region region;
	private String phoneNumber;
}
