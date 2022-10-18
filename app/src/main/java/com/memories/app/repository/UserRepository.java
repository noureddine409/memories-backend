package com.memories.app.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.memories.app.model.User;

public interface UserRepository extends GenericRepository<User> {
	Optional<User> findByEmail(String email);
	
	Optional<User> findByVerificationCode(String verificationCode);
	
	@Query("MATCH (follower:User), (following:User)\r\n"
			+ "WHERE id(follower)=$idFollower\r\n"
			+ "AND id(following)=$idFollowing\r\n"
			+ "CREATE (follower)-[:FOLLOWS]->(following)")
	void addFollow(@Param("idFollower") Long idFollower, @Param("idFollowing") Long idFollowing);
	
	@Query("MATCH (follower:User)-[f:FOLLOWS]->(following:User)\r\n"
			+ "WHERE id(follower)=$idFollower\r\n"
			+ "AND id(following)=$idFollowing\r\n"
			+ "DELETE f")
	void deleteFollow(@Param("idFollower") Long idFollower, @Param("idFollowing") Long idFollowing);
	
	@Query("MATCH (follower:User), (following:User)\r\n"
			+ "WHERE id(follower)=$idFollower\r\n"
			+ "AND id(following)=$idFollowing\r\n"
			+ "RETURN EXISTS((follower)-[:FOLLOWS]->(following))")
	Boolean followExist(@Param("idFollower") Long idFollower, @Param("idFollowing") Long idFollowing);
	
	@Query("MATCH (followers:User)-[:FOLLOWS]->(following:User)\r\n"
			+ "WHERE id(following)=$id\r\n"
			+ "return followers")
	List<User> getFollowers(@Param("id") Long id);
	
	@Query("MATCH (follower:User)-[:FOLLOWS]->(following:User)\r\n"
			+ "WHERE id(follower)=$id\r\n"
			+ "return following")
	List<User> getFollowing(@Param("id") Long id);
	
} 
