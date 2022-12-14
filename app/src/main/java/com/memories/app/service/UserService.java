package com.memories.app.service;

import java.util.List;

import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.ForgetPasswordToken;
import com.memories.app.model.User;

public interface UserService extends GenericService<User> {
	
	public User findUserByEmail(String email) throws ElementNotFoundException;
	
	public List<User> getFollowers(Long id) throws ElementNotFoundException;
	
	public List<User> getFollowing(Long id) throws ElementNotFoundException;
	
	public Boolean addFollow(Long idFollower, Long idFollowing);
	
	public Boolean deleteFollow(Long idFollower, Long idFollowing);
	
	public Boolean followExist(Long idFollower, Long idFollowing);
	
	public void sendVerificationEmail(User user, String siteURL);
	
	public void generateVerificationCode(User user);
	
	public boolean verify(String code);
	
	public ForgetPasswordToken generateForgetPasswordToken(User user);
	
	public void sendForgetPasswordEmail(ForgetPasswordToken token, String siteURL);

	public boolean verifyForgetPasswordToken(ForgetPasswordToken token, String newPassword);
	
	public ForgetPasswordToken findForgetPasswordToken(String email) throws ElementNotFoundException;
	
	public void deleteNotActivatedAccounts() ;

}
