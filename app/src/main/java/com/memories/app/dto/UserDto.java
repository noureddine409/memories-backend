package com.memories.app.dto;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.memories.app.model.GenericEnum.Gender;
import com.memories.app.model.GenericEnum.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends GenericDto{
	
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate birthDay;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String password;
	private String profilePicture;
	private String backgroundPicture;
	private String bio;
	private Gender gender;
	private UserType type;
	private AdressDto adress;
	
	@JsonProperty(access = WRITE_ONLY)
	private List<RoleDto> roles;
	
}
