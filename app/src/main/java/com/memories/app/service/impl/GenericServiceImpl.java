package com.memories.app.service.impl;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import com.memories.app.commun.CoreConstant;
import com.memories.app.commun.CoreConstant.Exception;
import com.memories.app.exception.BusinessException;
import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.GenericEntity;
import com.memories.app.repository.GenericRepository;
import com.memories.app.service.GenericService;

@Service
public class GenericServiceImpl<T extends GenericEntity> implements GenericService<T> {
	
	final Logger LOG = LoggerFactory.getLogger(GenericServiceImpl.class);
	
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
        newEntity.setUpdatedAt(LocalDateTime.now());

        return genericRepository.save(newEntity);
    }
	@Override
    public T findById(final Long id) throws ElementNotFoundException {

        final Optional<T> entityExist = genericRepository.findById(id);
        if (entityExist.isPresent()) {
            return entityExist.get();
        } else {
        	LOG.warn(CoreConstant.Exception.NOT_FOUND);
            throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{id});
        }
    }
	@Override
    public boolean delete(final Long id) throws ElementNotFoundException {

        try {
            genericRepository.deleteById(id);
            return true;
        } catch (final BusinessException e) {
            LOG.error("Error",e);
            throw new BusinessException(null, e, CoreConstant.Exception.DELETE_ELEMENT, new Object[]{id});
        }
    }
	@Override
    public T save(final T entity) throws ElementAlreadyExistException {
        final Long id = entity.getId();
        final Optional<T> entityExist = genericRepository.findById(id);
        if (!entityExist.isPresent()) {
            return genericRepository.save(entity);
        } else {
        	LOG.warn(CoreConstant.Exception.ALREADY_EXISTS);
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.ALREADY_EXISTS, new Object[]{id});
        }
    }
	
	@Override
	public T partialUpdate(Long id, Map<String, Object> fields) throws ElementNotFoundException {
		
		final Optional<T> foundEntity = genericRepository.findById(id);
        if (!foundEntity.isPresent()) {
        	//LOG.warn(CoreConstant.Exception.NOT_FOUND);
            throw new ElementNotFoundException(null, new ElementNotFoundException(), CoreConstant.Exception.NOT_FOUND, new Object[]{id});
        }
        T newEntity = foundEntity.get();
        fields.forEach((k, v) -> {
        	Field field = ReflectionUtils.findField(newEntity.getClass(), k);
        	field.setAccessible(true);
        	ReflectionUtils.setField(field, newEntity, v);
        });
        newEntity.setUpdatedAt(LocalDateTime.now());
        
        return genericRepository.save(newEntity);
	}
	@Override
	public List<T> findAll() throws BusinessException {
		try {
			return genericRepository.findAll();
		}
		catch(BusinessException e){
			throw new BusinessException(null, e, Exception.FIND_ELEMENTS, null);
		}
	}

}
