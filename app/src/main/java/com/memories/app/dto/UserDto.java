package com.memories.app.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.memories.app.model.GenericEnum.Gender;
import com.memories.app.model.GenericEnum.UserType;
import com.memories.app.validator.ValidPassword;
import com.memories.app.validator.ValidPhoneNumber;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static com.memories.app.commun.CoreConstant.Validation.PASSWORD_SIZE_MAX;
import static com.memories.app.commun.CoreConstant.Validation.PASSWORD_SIZE_MIN;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class UserDto extends GenericDto{
	@NotBlank
	private String firstName;
	@NotBlank
	private String lastName;
	@Email
	@NotNull
	private String email;
	@ValidPhoneNumber 
	private PhoneNumberDto phoneNumber;
	private LocalDate birthDay;
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY) @ValidPassword @NotBlank
	@Length(min = PASSWORD_SIZE_MIN, max = PASSWORD_SIZE_MAX) @NotBlank
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
