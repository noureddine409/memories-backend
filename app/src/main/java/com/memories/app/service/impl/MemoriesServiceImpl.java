package com.memories.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memories.app.commun.CoreConstant;
import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.model.Memory;
import com.memories.app.repository.MemoryRepository;
import com.memories.app.service.MemoriesService;


@Service
public class MemoriesServiceImpl extends GenericServiceImpl<Memory> implements MemoriesService {
	
	@Autowired
	private MemoryRepository memoriesRepository;
	
	@Override
	public Boolean addLove(Long idUser, Long idMemory) {
		if(memoriesRepository.loveExist(idUser, idMemory)) {
			LOG.warn(CoreConstant.Exception.ALREADY_EXISTS);
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.ALREADY_EXISTS, new Object[]{idUser, idMemory});
		}
		memoriesRepository.addLove(idUser, idMemory);
		return Boolean.TRUE;
	}

	@Override
	public Boolean deleteLove(Long idUser, Long idMemory) {
		if(!memoriesRepository.loveExist(idUser, idMemory)) {
			LOG.warn(CoreConstant.Exception.ALREADY_EXISTS);
            throw new ElementAlreadyExistException(null, new ElementAlreadyExistException(), CoreConstant.Exception.ALREADY_EXISTS, new Object[]{idUser, idMemory});
		}
		memoriesRepository.deleteLove(idUser, idMemory);
		return Boolean.TRUE;
	}

	@Override
	public Boolean loveExists(Long idUser, Long idMemory) {
		return memoriesRepository.loveExist(idUser, idMemory);
	}
}
