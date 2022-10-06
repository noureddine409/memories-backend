package com.memories.app.repository;

import java.util.Optional;

import com.memories.app.model.User;

public interface UserRepository extends GenericRepository<User> {
	Optional<User> findByEmail(String email);
}
