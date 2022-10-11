package com.memories.app.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.memories.app.model.Comment;

public interface CommentRepository extends GenericRepository<Comment> {
	
	@Query("MATCH (comments:Comment)-[:RECEIVED]->(memory:Memory) \r\n"
			+ "WHERE id(memory)=$idMemory \r\n"
			+ "RETURN comments")
	List<Comment> commentsOfMemories(@Param("idMemory") Long id);

}
