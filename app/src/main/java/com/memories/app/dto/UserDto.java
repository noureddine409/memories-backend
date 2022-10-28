package com.memories.app.dto;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

import java.time.LocalDate;
import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.memories.app.model.GenericEnum.Gender;
import com.memories.app.model.GenericEnum.UserType;
import com.memories.app.validator.ValidPassword;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends GenericDto{
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Email
	private String email;
	private String phoneNumber;
	private LocalDate birthDay;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @ValidPassword @NotBlank
	private String password;
	private String profilePicture;
	private String backgroundPicture;
	private String bio;
	@NotNull
	private Gender gender;
	@NotNull
	private UserType type;
	private AdressDto adress;
	
	@JsonProperty(access = WRITE_ONLY)
	private List<RoleDto> roles;
	
}
