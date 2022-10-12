package com.memories.app.service;

import com.memories.app.model.Memory;

public interface MemoriesService extends GenericService<Memory> {
	public Boolean addLove(Long idUser, Long idMemory);
	public Boolean deleteLove(Long idUser, Long idMemory);
	public Boolean loveExists(Long idUser, Long idMemory);
}
