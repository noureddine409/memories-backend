package com.memories.app.service;

import java.util.List;

import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.Comment;

public interface CommentService extends GenericService<Comment> {
	
	public List<Comment> getCommentsByMemory(Long id) throws ElementNotFoundException;
	
}
