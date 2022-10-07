package com.memories.app.service;

import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.GenericEntity;

public interface GenericService<T extends GenericEntity> {
	public T update(final Long id, final T entity) throws ElementNotFoundException ;
	
	T findById(final Long id) throws ElementNotFoundException;

    T save(final T entity) throws ElementAlreadyExistException;
    
    boolean delete(final Long id) throws ElementNotFoundException;
}
