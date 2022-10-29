package com.memories.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.memories.app.dto.CommentDto;
import com.memories.app.model.Comment;
import com.memories.app.model.Memory;
import com.memories.app.model.User;
import com.memories.app.service.CommentService;
import com.memories.app.service.MemoriesService;



@RestController
@RequestMapping("api/comments")
public class CommentController extends GenericController<Comment, CommentDto> {
	
	@Autowired
	private CommentService commentService;
	
	@Autowired
	private MemoriesService memoriesService;
	
	@GetMapping("/{id}")
	public ResponseEntity<CommentDto> getById(@PathVariable Long id){
		return new ResponseEntity<>(convertToDto(commentService.findById(id)), HttpStatus.OK);
	}
	
	@GetMapping("/memory/{id}")
	public ResponseEntity<List<CommentDto>> getMemoryComments(@PathVariable Long id) {
		return new ResponseEntity<>(convertListToDto(commentService.getCommentsByMemory(id), CommentDto.class), HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CommentDto> save(@Valid @RequestBody CommentDto commentDto) {
		final User currentUser = getCurrentUser();
		final Comment entity = convertToEntity(commentDto);
		final Memory memory = memoriesService.findById(commentDto.getMemory().getId());
		
		
		entity.setMemory(memory);
		entity.setOwner(currentUser);
		return new ResponseEntity<CommentDto>(convertToDto(commentService.save(entity)), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Boolean> delete(@PathVariable Long id){
		final User currentUser = getCurrentUser();
		final Comment comment = commentService.findById(id);
		
		if(isOwner(currentUser, comment)) {
			return new ResponseEntity<>(commentService.delete(id), HttpStatus.OK);
		}
		return new ResponseEntity<>(Boolean.FALSE, HttpStatus.UNAUTHORIZED);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<CommentDto> update(@PathVariable Long id, @RequestParam("newComment") String newComment) {
		final User currentUser = getCurrentUser();
		final Comment comment = commentService.findById(id);
		
		if(isOwner(currentUser, comment)) {
			comment.setComment(newComment);
			return new ResponseEntity<>(convertToDto(commentService.update(id, comment)), HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	private Boolean isOwner(User currentUser, Comment comment) {
		if(currentUser.getEmail().equals(comment.getOwner().getEmail())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

}
