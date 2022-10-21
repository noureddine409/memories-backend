package com.memories.app.repository;

import java.util.Optional;

import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;

import com.memories.app.model.ForgetPasswordToken;

public interface forgetPasswordTokenRepository extends GenericRepository<ForgetPasswordToken>{
	Optional<ForgetPasswordToken> findByToken(String token);
	
	@Query("MATCH (forget:ForgetPasswordToken)-[:USER]->(user:User)\r\n"
			+ "WHERE user.email = $email\r\n"
			+ "RETURN forget")
	Optional<ForgetPasswordToken> findByUser(@Param("email") String email);
}
