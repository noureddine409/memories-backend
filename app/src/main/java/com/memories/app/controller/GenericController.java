package com.memories.app.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.memories.app.dto.GenericDto;
import com.memories.app.model.GenericEntity;
import com.memories.app.utils.ClassTypeProvider;

public abstract class GenericController <T extends GenericEntity, D extends GenericDto> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private ClassTypeProvider classTypeProvider;
	
	protected Class<?>[] getClasses(){
		return classTypeProvider.getClasses(this, GenericController.class);
	}
	
	protected T convertToEntity(D dto) {
		return (T) modelMapper.map(dto, getClasses()[0]);
	}
	
	protected D convertToDto(T entity) {
		return (D) modelMapper.map(entity, getClasses()[1]);
	}
	
	protected List<D> convertListToDto(List<T> source, Class<D> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
	
}
