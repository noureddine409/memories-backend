package com.memories.app.repository;

import java.util.Optional;

import com.memories.app.model.ForgetPasswordToken;

public interface forgetPasswordTokenRepository extends GenericRepository<ForgetPasswordToken>{
	Optional<ForgetPasswordToken> findByToken(String token);
}
