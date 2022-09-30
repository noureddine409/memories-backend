package com.memories.app.dto;

import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenericDto {
	
	private Long id;
	private LocalDateTime createdAt;
	private LocalDateTime updatedat;
}
