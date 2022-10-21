package com.memories.app.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
	
	@CreatedDate
	private LocalDateTime createdAt;
	
	@LastModifiedDate
	private LocalDateTime updatedAt;
	
	public void setCreatedAt(LocalDateTime date) {
		this.createdAt = (createdAt == null ? LocalDateTime.now():date);

	}
	
}
