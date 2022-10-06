package com.memories.app.service;

import com.memories.app.model.User;

public interface UserService extends GenericService<User> {
	
	public User findUserByEmail(String email);
	public User findById(Long id);
	public User saveUser(User user);
	

}
