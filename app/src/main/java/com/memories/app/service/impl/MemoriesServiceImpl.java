package com.memories.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.memories.app.commun.CoreConstant;
import com.memories.app.exception.ElementAlreadyExistException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.Location;
import com.memories.app.model.Media;
import com.memories.app.model.Memory;
import com.memories.app.repository.LocationRepository;
import com.memories.app.repository.MediaRepository;
import com.memories.app.repository.MemoryRepository;
import com.memories.app.service.MemoriesService;


@Service
public class MemoriesServiceImpl extends GenericServiceImpl<Memory> implements MemoriesService {
	
	@Autowired
	private MemoryRepository memoriesRepository;
	
	@Autowired
	private MediaRepository mediaRep;
	
	@Autowired
	private LocationRepository locationRepository;
	
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
	
	@Override
	public boolean delete(Long id) throws ElementNotFoundException {
		final Memory memoryFound = memoriesRepository.findById(id).get();
		if(memoryFound == null) throw new ElementNotFoundException();
		
		final List<Media> medias = memoryFound.getMedias();
		if(medias != null) {
			for(Media m : medias) {
				mediaRep.delete(m);
			}
		}
		final Location location = memoryFound.getLocation();
		if(location!=null)
		locationRepository.deleteById(location.getId());;
		
		return super.delete(id);
	}
}
