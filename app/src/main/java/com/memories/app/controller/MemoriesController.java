package com.memories.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.memories.app.dto.MemoryDto;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.model.Memory;
import com.memories.app.model.User;
import com.memories.app.service.MemoriesService;

@RestController
@RequestMapping("api/memories")
public class MemoriesController extends GenericController<Memory, MemoryDto> {
	
	@Autowired
	private MemoriesService memoriesService;
	
	@GetMapping
	ResponseEntity<List<MemoryDto>> getAll() {
		List<Memory> entities = memoriesService.findAll();
		convertListToDto(entities, MemoryDto.class);
		return new ResponseEntity<List<MemoryDto>>(convertListToDto(entities, MemoryDto.class), HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	ResponseEntity<MemoryDto> getMemoryById(@PathVariable Long id){
		return new ResponseEntity<MemoryDto>(convertToDto(memoriesService.findById(id)), HttpStatus.OK);
	}
	@PostMapping
	ResponseEntity<MemoryDto> saveMemorie(@RequestBody MemoryDto memoryDto){
		final Memory entity = convertToEntity(memoryDto);
		final User currentUser = getCurrentUser();
		entity.setCreatedBy(currentUser);
		Memory saved = memoriesService.save(entity);
		return new ResponseEntity<MemoryDto>(convertToDto(saved), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> deleteMemorie(@PathVariable Long id) throws ElementNotFoundException {	
		
		if(isOwner(getCurrentUser(), memoriesService.findById(id)))  {
			return new ResponseEntity<>(memoriesService.delete(id), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(Boolean.FALSE, HttpStatus.UNAUTHORIZED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<MemoryDto> updateMemory(@PathVariable Long id, @RequestBody MemoryDto memoryDto)
	 throws ElementNotFoundException {
		final User currentUser = getCurrentUser();
		if(isOwner(currentUser, memoriesService.findById(id))) {
			memoryDto.setId(id);
			Memory converted = convertToEntity(memoryDto);
			converted.setCreatedBy(currentUser);
			Memory saved = memoriesService.update(id, converted);
			return new ResponseEntity<MemoryDto>(convertToDto(saved), HttpStatus.OK);
		}
		
		return new ResponseEntity<>(memoryDto, HttpStatus.UNAUTHORIZED);
	}
	
	@PatchMapping("/{id}")
	public ResponseEntity<MemoryDto> patch(@PathVariable Long id, @RequestBody MemoryDto memoryDto)
			 throws ElementNotFoundException, IllegalArgumentException, IllegalAccessException {
		final User currentUser = getCurrentUser();
		if(isOwner(currentUser, memoriesService.findById(id))) {
			Memory updated = memoriesService.partialUpdate(id, convertToMap(memoryDto));
			return new ResponseEntity<MemoryDto>(convertToDto(updated), HttpStatus.OK);
		}
		return new ResponseEntity<>(memoryDto, HttpStatus.UNAUTHORIZED);
	}
	
	private Boolean isOwner(User currentUser, Memory memory) {
		if(currentUser.getEmail().equals(memory.getCreatedBy().getEmail())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
		
}
