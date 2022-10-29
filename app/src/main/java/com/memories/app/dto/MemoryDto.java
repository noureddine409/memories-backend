package com.memories.app.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
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
	@JsonProperty(access = Access.READ_ONLY)
	private UserDto createdBy;
	private List<UserDto> mensioned;
	
}
