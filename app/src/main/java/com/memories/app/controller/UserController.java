package com.memories.app.controller;

import com.memories.app.commun.CoreConstant;
import com.memories.app.dto.ResetPwdDto;
import com.memories.app.dto.SearchDto;
import com.memories.app.dto.UserDto;
import com.memories.app.dto.UserPatchDto;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.exception.UnauthorizedFileFormatException;
import com.memories.app.model.User;
import com.memories.app.service.AwsS3Service;
import com.memories.app.service.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/users")
public class UserController extends GenericController<User, UserDto> {
	
	private final ModelMapper modelMapper;
	
	private final PasswordEncoder passwordEncoder;
	
	private final UserService userService;
	
	
	
	private final AwsS3Service awsS3Service;

	public UserController(ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserService userService, AwsS3Service awsS3Service) {
		this.modelMapper = modelMapper;
		this.passwordEncoder = passwordEncoder;
		this.userService = userService;
		this.awsS3Service = awsS3Service;
	}

	@GetMapping
	ResponseEntity<List<UserDto>> getAll(@RequestBody SearchDto search) {
		List<User> entities = userService.findAll(PageRequest.of(search.getPage(), search.getSize()) , search.getFilters()).getContent();
		return new ResponseEntity<>(convertListToDto(entities, UserDto.class), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> get(@PathVariable Long id) {
		return new ResponseEntity<>(convertToDto(userService.findById(id)) , HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<UserDto> patch(@PathVariable Long id, @Valid @RequestBody UserPatchDto dto) throws ElementNotFoundException, IllegalArgumentException, IllegalAccessException{
		final User currentUser = getCurrentUser();
		if(currentUser.getEmail().equals(userService.findById(id).getEmail())) {
			User updated = userService.partialUpdate(id, convertToMap(dto));
			return new ResponseEntity<>(convertToDto(updated), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PostMapping("/follows/{idFollowing}")
	public ResponseEntity<Boolean> follow(@PathVariable Long idFollowing) {
		final Long id = getCurrentUser().getId();
		if(userService.followExist(id, idFollowing)) {
			userService.deleteFollow(id, idFollowing);
			return new ResponseEntity<>(Boolean.FALSE, HttpStatus.OK);
		}
		userService.addFollow(id, idFollowing);
		return new ResponseEntity<>(Boolean.TRUE, HttpStatus.OK);
	}
	
	@GetMapping("/followers/{id}")
	public ResponseEntity<List<UserDto>> getFollowers(@PathVariable Long id) throws ElementNotFoundException {
		userService.findById(id);
		return new ResponseEntity<>(convertListToDto(userService.getFollowers(id), UserDto.class), HttpStatus.OK);
		
	}
	
	@GetMapping("/following/{id}")
	public ResponseEntity<List<UserDto>> getFollowing(@PathVariable Long id) throws ElementNotFoundException{
		userService.findById(id);
		return new ResponseEntity<>(convertListToDto(userService.getFollowing(id), UserDto.class), HttpStatus.OK);
		
	}
	
	@PatchMapping("/profilePic/{id}")
	public ResponseEntity<UserDto> updateProfilePic(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		
		if(!file.getContentType().contains("image"))
			throw new UnauthorizedFileFormatException(null, new UnauthorizedFileFormatException(), CoreConstant.Exception.FILE_UNAUTHORIZED_FORMAT, new Object[]{});
		User userFound = userService.findById(id);
		final User currentUser = getCurrentUser();
		if(currentUser.getEmail().equals(userService.findById(id).getEmail())) {
			final String url = awsS3Service.save(file);
			userFound.setProfilePicture(url);
			return new ResponseEntity<>(convertToDto(userService.update(id, currentUser)), HttpStatus.OK);
			}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@PatchMapping("/backgroundPic/{id}")
	public ResponseEntity<UserDto> updatebackgroundPic(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
		if(!file.getContentType().contains("image"))
			throw new UnauthorizedFileFormatException(null, new UnauthorizedFileFormatException(), CoreConstant.Exception.FILE_UNAUTHORIZED_FORMAT, new Object[]{});
		User userFound = userService.findById(id);
		final User currentUser = getCurrentUser();
		if(currentUser.getEmail().equals(userService.findById(id).getEmail())) {
			final String url = awsS3Service.save(file);
			userFound.setBackgroundPicture(url);
			return new ResponseEntity<UserDto>(convertToDto(userService.update(id, currentUser)), HttpStatus.OK);
			}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	@PatchMapping("/resetPassword")
	public ResponseEntity<UserDto> resetPwd(@RequestBody ResetPwdDto dto ) {
		User currentUser = getCurrentUser();
		if(passwordEncoder.matches(dto.getOldPassword(), currentUser.getPassword())) {
			currentUser.setPassword(passwordEncoder.encode(dto.getNewPassword()));
			User savedUser = userService.update(getCurrentUserId(), currentUser);
			return new ResponseEntity<>(convertToDto(savedUser), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	private Map<String, Object> convertToMap(UserPatchDto dto) throws IllegalArgumentException, IllegalAccessException {
		User obj = convertToEntity(dto);
		Map<String, Object> converted = new HashMap<>();
		Field[] fields = obj.getClass().getDeclaredFields();
		for(Field field: fields) {
			field.setAccessible(true);
			Object value = field.get(obj);
			if(value!=null)
			converted.put(field.getName(), value);
		}
		return converted;
	}
	
	
	
	private User convertToEntity(UserPatchDto dto) {
		return modelMapper.map(dto, User.class);
	}
	

}
