package com.memories.app.controller;



import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.memories.app.commun.CoreConstant.Exception;
import com.memories.app.dto.GenericDto;
import com.memories.app.exception.BusinessException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.GenericEntity;
import com.memories.app.model.User;
import com.memories.app.service.UserService;
import com.memories.app.utils.ClassTypeProvider;

public abstract class GenericController <T extends GenericEntity, D extends GenericDto> {
	
	final Logger LOG = LoggerFactory.getLogger(GenericController.class);
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ClassTypeProvider classTypeProvider;
	
	protected Class<?>[] getClasses(){
		return classTypeProvider.getClasses(this, GenericController.class);
	}
	
	@SuppressWarnings("unchecked")
	protected T convertToEntity(D dto) {
		return (T) modelMapper.map(dto, getClasses()[0]);
	}
	
	@SuppressWarnings("unchecked")
	protected D convertToDto(T entity) {
		return (D) modelMapper.map(entity, getClasses()[1]);
	}
	
	protected List<D> convertListToDto(List<T> source, Class<D> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
	
	protected Long getCurrentUserId() throws  BusinessException{
		final Authentication authentication = SecurityContextHolder.getContext()
				.getAuthentication();

	if(Objects.isNull(authentication.getPrincipal())){
		LOG.error(Exception.AUTHENTICATION_NULL_PRINCIPAL);
	    throw new BusinessException(null, new BusinessException(), Exception.AUTHENTICATION_NULL_PRINCIPAL, null);
    }
	return (Long) authentication.getPrincipal();
	}
	
	protected User getCurrentUser() throws ElementNotFoundException {
		return userService.findById(getCurrentUserId());
	}
	
}
