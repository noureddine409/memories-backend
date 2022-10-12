package com.memories.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories.app.commun.CoreConstant.Pagination;
import com.memories.app.dto.SearchDto;
import com.memories.app.dto.UserDto;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.User;
import com.memories.app.service.UserService;

@RestController
@RequestMapping("api/users")
public class UserController extends GenericController<User, UserDto> {
	
	@Autowired
	private UserService userService;
	@GetMapping
	ResponseEntity<List<UserDto>> getAll(@RequestBody SearchDto search) {
		List<User> entities = userService.findAll(PageRequest.of(search.getPage(), search.getSize()) , search.getFilters()).getContent();
		return new ResponseEntity<List<UserDto>>(convertListToDto(entities, UserDto.class), HttpStatus.OK);
	}
	
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
	
	@PostMapping("/follows/{idFollowing}")
	public ResponseEntity<Boolean> follow(@PathVariable Long idFollowing) {
		final Long id = getCurrentUser().getId();
		if(userService.followExist(id, idFollowing)) {
			userService.deleteFollow(id, idFollowing);
			return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.OK);
		}
		userService.addFollow(id, idFollowing);
		return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
	}
	
	@GetMapping("/followers/{id}")
	public ResponseEntity<List<User>> getFollowers(@PathVariable Long id) throws ElementNotFoundException {
		userService.findById(id);
		return new ResponseEntity<>(userService.getFollowers(id), HttpStatus.OK);
		
	}
	
	@GetMapping("/following/{id}")
	public ResponseEntity<List<User>> getFollowing(@PathVariable Long id) throws ElementNotFoundException{
		userService.findById(id);
		return new ResponseEntity<>(userService.getFollowing(id), HttpStatus.OK);
		
	}
	

}
