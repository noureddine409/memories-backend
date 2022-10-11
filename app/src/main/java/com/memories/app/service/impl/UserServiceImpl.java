package com.memories.app.service.impl;

import java.util.List;
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
	public User save(User user) throws ElementAlreadyExistException {
		if (userRepository.findByEmail(user.getEmail()) == null)
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.ALREADY_EXISTS,
                    new Object[]{user.getEmail()});

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        
        return userRepository.save(user);
	}

	@Override
	public User findById(Long id) throws ElementNotFoundException{
		Optional<User> user = userRepository.findById(id);
		if (user.isPresent()) {
			return user.get();
		}
		throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {id});
	}

	@Override
	public List<User> getFollowers(Long id) throws ElementNotFoundException {
		
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent())
			return userRepository.getFollowers(id);
		throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {id});
	}

	@Override
	public List<User> getFollowing(Long id) throws ElementNotFoundException {
		Optional<User> user = userRepository.findById(id);
		if(user.isPresent())
			return userRepository.getFollowing(id);
		throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {id});
	}

	@Override
	public Boolean addFollow(Long idFollower, Long idFollowing) throws ElementAlreadyExistException {
		if(userRepository.followExist(idFollower, idFollowing)){
			LOG.warn(CoreConstant.Exception.ALREADY_EXISTS);
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.ALREADY_EXISTS, new Object[]{idFollower, idFollowing});
		}
		userRepository.addFollow(idFollower, idFollowing);
		return Boolean.TRUE;
		}

	@Override
	public Boolean deleteFollow(Long idFollower, Long idFollowing) {
		if(!userRepository.followExist(idFollower, idFollowing)){
			LOG.warn(CoreConstant.Exception.DELETE_ELEMENT);
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.DELETE_ELEMENT, new Object[]{idFollower, idFollowing});
		}
		userRepository.deleteFollow(idFollower, idFollowing);
		return Boolean.TRUE;
	}

	@Override
	public Boolean followExist(Long idFollower, Long idFollowing) {
		return userRepository.followExist(idFollower, idFollowing);
	}


}
