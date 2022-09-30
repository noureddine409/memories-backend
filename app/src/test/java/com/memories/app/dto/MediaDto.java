package com.memories.app.dto;

import com.memories.app.model.GenericEnum.MediaType;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class MediaDto extends GenericDto {
	private String path;
	private MediaType type;
	private MemoryDto memory;
}
