package com.memories.app.model;

import java.time.LocalDateTime;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public abstract class GenericEntity {
	public GenericEntity(Long id) {
		this.id = id;
	}

	@Id
	@GeneratedValue
	private Long id;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
}
