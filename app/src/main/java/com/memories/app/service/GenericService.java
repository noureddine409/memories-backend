package com.memories.app.service;

import java.util.List;
import java.util.Map;

import com.memories.app.exception.BusinessException;
import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.GenericEntity;

public interface GenericService<T extends GenericEntity> {
	public T update(final Long id, final T entity) throws ElementNotFoundException ;
	
	public T partialUpdate(final Long id, final Map<String, Object> fields) throws ElementNotFoundException ;
	
	T findById(final Long id) throws ElementNotFoundException;

    T save(final T entity) throws ElementAlreadyExistException;
    
    boolean delete(final Long id) throws ElementNotFoundException;
    
    // TODO add pagination + sort + filter
    public List<T> findAll() throws BusinessException;
}
