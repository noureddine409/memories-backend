package com.memories.app.service.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.memories.app.commun.CoreConstant;
import com.memories.app.dto.Filter;
import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.ForgetPasswordToken;
import com.memories.app.model.User;
import com.memories.app.repository.UserRepository;
import com.memories.app.repository.forgetPasswordTokenRepository;
import com.memories.app.service.EmailSenderService;
import com.memories.app.service.UserService;

import net.bytebuddy.utility.RandomString;

@Service
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private EmailSenderService senderService;
	
	@Autowired
	private forgetPasswordTokenRepository tokenRepository;
	
	@Value("${form.forget-password.token.expiry}")
	private int tokenExpiryMinutes;
	
	@Override
	public User findUserByEmail(String email) throws ElementNotFoundException {
		Optional<User> user = userRepository.findByEmail(email);
		if(user.isPresent()) return user.get();
		throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {email});
	}

	@Override
	public User save(User user) throws ElementAlreadyExistException {
		if (userRepository.findByEmail(user.getEmail()).isPresent())
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
	
	@Override
	protected Example<User> preparedFilters(List<Filter> filters) throws IllegalAccessException, NoSuchFieldException {
		Example<User> example = null;

        if (!CollectionUtils.isEmpty(filters)) {
        	final User user = new User();
        	
        	for (final Filter filter: filters) {
        		final Field fieldName = user.getClass().getDeclaredField(filter.getFilterName());
        		if(fieldName !=null) {
        			fieldName.setAccessible(true);
        			fieldName.set(user, filter.getAppliedValue());
        		}
        	}
        	example = Example.of(user, ExampleMatcher.matchingAny());
        }
        
        return example;
	}

	@Override
	public void sendVerificationEmail(User user, String siteURL) {
		String subject = "Please Verify your email address";
		
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.put("token", user.getVerificationCode());
		mailModel.put("user", user);
		mailModel.put("signature", "http://memories-app.com");
		mailModel.put("activationUrl", siteURL + "api/auth/verify?code=" + user.getVerificationCode());
		
		senderService.sendEmail(user.getEmail(), subject, mailModel, "activate-account.html");
	}

	@Override
	public void generateVerificationCode(User user) {
		String randomCode = RandomString.make(64);
		user.setVerificationCode(randomCode);
		user.setEnabled(false);
		
	}

	@Override
	public boolean verify(String code) {
		Optional<User> userFound = userRepository.findByVerificationCode(code);
		
		if(userFound.isEmpty())
			return false;
		else {
			User user = userFound.get();
			if(user.isEnabled())
				return false;
			user.setVerificationCode(null);
			user.setEnabled(true);
			userRepository.save(user);
			
			return true;
		}
	}

	@Override
	public ForgetPasswordToken generateForgetPasswordToken(User user) {
		
		final String randomCode = RandomString.make(64);
		
		ForgetPasswordToken forget = tokenRepository.findByUser(user.getEmail()).orElse(null);
		if(forget == null) {
			ForgetPasswordToken token = new ForgetPasswordToken();
			token.setUser(user);
			token.setToken(randomCode);
			token.setExpiryDate(tokenExpiryMinutes);
			tokenRepository.save(token);
			return token;
		}
		
		forget.setToken(randomCode);
		forget.setExpiryDate(tokenExpiryMinutes);
		forget.setUser(user);
		return tokenRepository.save(forget);
		
	}

	@Override
	public void sendForgetPasswordEmail(ForgetPasswordToken token, String siteURL) {
		String subject = "reset password";
		final User user = token.getUser();
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.put("token", token);
		mailModel.put("user", user);
		mailModel.put("signature", "http://memories-app.com");
		mailModel.put("resetUrl", siteURL + "api/auth/resetPassword?code=" + token.getToken());
		
		senderService.sendEmail(user.getEmail(), subject, mailModel, "reset-password.html");


		
	}

	@Override
	public boolean verifyForgetPasswordToken(ForgetPasswordToken token, String newPassword) {
		ForgetPasswordToken found = tokenRepository.findByToken(token.getToken()).orElse(null);
		if(found == null || found.isExpired()) {
			return false;
		}
		User user = found.getUser();
		user.setPassword(passwordEncoder.encode(newPassword));
		userRepository.save(user);
		tokenRepository.delete(found);
		return true;
	}

	@Override
	public ForgetPasswordToken findForgetPasswordToken(String email) throws ElementNotFoundException {
		Optional<ForgetPasswordToken> found = tokenRepository.findByUser(email);
		if (found.isPresent()) {
			ForgetPasswordToken token = found.get();
			token.setUser(tokenRepository.findById(token.getId()).get().getUser());
			return token;
		}
		throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[] {email});
	}

	@Override
	public void deleteNotActivatedAccounts() {
		userRepository.deleteNotActivatedAccounts();
		
	}
}