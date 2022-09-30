package com.memories.app.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommentDto extends GenericDto{
	private String comment;
	private UserDto owner;
	private MemoryDto memory;
}
