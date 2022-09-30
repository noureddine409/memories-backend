package com.memories.app.model;

import java.time.LocalDateTime;

import org.springframework.data.neo4j.core.schema.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class GenericEntity {
	@Id
	private Long id;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
}
