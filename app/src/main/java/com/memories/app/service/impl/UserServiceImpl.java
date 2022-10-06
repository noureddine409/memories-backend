package com.memories.app.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.memories.app.commun.CoreConstant;
import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.User;
import com.memories.app.repository.UserRepository;
import com.memories.app.service.UserService;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public User findUserByEmail(String email) throws ElementNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) return user.get();
		throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {email});
	}

	@Override
	public User saveUser(User user) {
		if (userRepository.findByEmail(user.getEmail()) == null)
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.ALREADY_EXISTS,
                    new Object[]{user.getEmail()});

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
	}

	@Override
	public User findById(Long id) {
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {id});
	}

}
