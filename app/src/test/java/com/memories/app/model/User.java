package com.memories.app.model;

import java.time.LocalDate;

import org.springframework.data.neo4j.core.schema.Node;

import com.memories.app.model.GenericEnum.Gender;
import com.memories.app.model.GenericEnum.UserType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Node
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class User extends GenericEntity{
	private String firstName;
	private String lastName;
	private String email;
	private String phoneNumber;
	private LocalDate birthDay;
	private String password;
	private String profilePicture;
	private String backgroundPicture;
	private String bio;
	private Gender gender;
	private UserType type;
	
}
