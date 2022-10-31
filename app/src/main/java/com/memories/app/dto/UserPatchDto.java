package com.memories.app.dto;

import java.time.LocalDate;

import com.memories.app.model.GenericEnum.Gender;
import com.memories.app.model.GenericEnum.UserType;
import com.memories.app.validator.ValidField;
import com.memories.app.validator.ValidPhoneNumber;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserPatchDto extends GenericDto {
	
	@ValidField
	private String firstName;
	@ValidField
	private String lastName;
	
	@ValidPhoneNumber
	private PhoneNumberDto phoneNumber;
	
	private LocalDate birthDay;
	@ValidField
	private String bio;
	
	private Gender gender;
	
	private UserType type;
	private AdressDto adress;
	
}
