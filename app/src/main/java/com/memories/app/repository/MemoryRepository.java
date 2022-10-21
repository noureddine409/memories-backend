package com.memories.app.repository;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.memories.app.model.Memory;

public interface MemoryRepository extends GenericRepository<Memory> {
	@Query("MATCH (user:User), (memory:Memory)\r\n"
			+ "WHERE id(user)=$idUser AND id(memory)=$idMemory \r\n"
			+ "CREATE (user)-[:LOVES]->(memory)")
	void addLove(@Param("idUser") Long iduser, @Param("idMemory") Long idmemory);
	
	@Query("MATCH (user:User)-[l:LOVES]-> (memory:Memory)\r\n"
			+ "WHERE id(user)=$idUser AND id(memory)=$idMemory \r\n"
			+ "DELETE l")
	void deleteLove(@Param("idUser") Long iduser, @Param("idMemory") Long idmemory);
	
	@Query("MATCH (user:User), (memory:Memory)\r\n"
			+ "WHERE id(user)=$idUser AND id(memory)=$idMemory\r\n"
			+ "RETURN EXISTS( (user)-[:LOVES]-> (memory)  )")
	Boolean loveExist(@Param("idUser") Long iduser, @Param("idMemory") Long idmemory);
}
