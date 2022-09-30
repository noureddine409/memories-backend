package com.memories.app.repository;

import org.springframework.data.neo4j.repository.Neo4jRepository;

import com.memories.app.model.GenericEntity;

public interface GenericRepository<T extends GenericEntity> extends Neo4jRepository<T , Long> {
	
}
