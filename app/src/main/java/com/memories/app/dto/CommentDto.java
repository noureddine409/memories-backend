package com.memories.app.dto;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentDto extends GenericDto{
	@NotBlank
	private String comment;
	@JsonProperty(access = Access.READ_ONLY)
	private UserDto owner;
	private MemoryDto memory;
}
