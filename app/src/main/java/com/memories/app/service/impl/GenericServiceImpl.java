package com.memories.app.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memories.app.commun.CoreConstant;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.GenericEntity;
import com.memories.app.repository.GenericRepository;
import com.memories.app.service.GenericService;

@Service
public class GenericServiceImpl<T extends GenericEntity> implements GenericService<T> {
	
	@Autowired
	private GenericRepository<T> genericRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
    public T update(final Long id, final T entity) throws ElementNotFoundException {
        final Optional<T> foundEntity = genericRepository.findById(id);

        if (!foundEntity.isPresent()) {
        	//LOG.warn(CoreConstant.Exception.NOT_FOUND);
            throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{id});
        }

        T newEntity = foundEntity.get();
        entity.setCreatedAt(newEntity.getCreatedAt());
        modelMapper.map(entity, newEntity);
        newEntity.setId(id);

        return genericRepository.save(newEntity);
    }

}
