package com.memories.app.service;

import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.User;

public interface UserService extends GenericService<User> {
	
	public User findUserByEmail(String email) throws ElementNotFoundException;
	

}
