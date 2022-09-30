package com.memories.app.dto;

import java.util.List;

import com.memories.app.model.GenericEnum.Activities;
import com.memories.app.model.GenericEnum.Audience;
import com.memories.app.model.GenericEnum.Feeling;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MemoryDto extends GenericDto {
	
	
	private String body;
	private boolean isFavorite;
	private Audience audience;
	private List<MediaDto> medias;
	private LocationDto location;
	private Feeling feeling;
	private Activities activity;
	private UserDto createdBy;
	private List<UserDto> mensioned;
	private List<CommentDto> comments;
	private List<UserDto> lovers;
	
}
