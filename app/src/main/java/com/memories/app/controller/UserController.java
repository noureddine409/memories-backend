package com.memories.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories.app.dto.UserDto;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.User;
import com.memories.app.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController extends GenericController<User, UserDto> {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/{id}")
	public ResponseEntity<UserDto> get(@PathVariable Long id) {
		return new ResponseEntity<>(convertToDto(userService.findById(id)) , HttpStatus.OK);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<UserDto> patch(@PathVariable Long id, @RequestBody UserDto dto) throws ElementNotFoundException, IllegalArgumentException, IllegalAccessException{
		final User currentUser = getCurrentUser();
		if(currentUser.getEmail().equals(userService.findById(id).getEmail())) {
			User updated = userService.partialUpdate(id, convertToMap(dto));
			return new ResponseEntity<UserDto>(convertToDto(updated), HttpStatus.OK);
		}
		return new ResponseEntity<>(dto, HttpStatus.UNAUTHORIZED);
	}

}
