package com.memories.app.service;

import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.GenericEntity;

public interface GenericService<T extends GenericEntity> {
	public T update(final Long id, final T entity) throws ElementNotFoundException ;
}
