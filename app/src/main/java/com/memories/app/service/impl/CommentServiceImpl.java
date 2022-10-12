package com.memories.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.Comment;
import com.memories.app.repository.CommentRepository;
import com.memories.app.service.CommentService;
import com.memories.app.service.MemoriesService;

@Service
public class CommentServiceImpl extends GenericServiceImpl<Comment> implements CommentService {
	
	@Autowired
	private MemoriesService memoriesService;
	
	@Autowired
	private CommentRepository commentRepository;

	@Override
	public List<Comment> getCommentsByMemory(Long id) throws ElementNotFoundException {
		memoriesService.findById(id);
		return commentRepository.commentsOfMemories(id);
	}

}
