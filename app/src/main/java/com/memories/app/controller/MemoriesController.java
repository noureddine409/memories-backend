package com.memories.app.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.memories.app.dto.MemoryDto;
import com.memories.app.model.GenericEnum.Feeling;
import com.memories.app.model.Memory;
import com.memories.app.model.User;

@RestController
@RequestMapping("api/memories")
public class MemoriesController extends GenericController<Memory, MemoryDto> {
	
	
	
	@GetMapping
	public ResponseEntity<MemoryDto> func() {
		User user = new User();
		user.setEmail("email");;
		Memory mem = new Memory();
		mem.setBody("body");
		mem.setCreatedBy(user);
		MemoryDto dto = convertToDto(mem);
		return new ResponseEntity<MemoryDto>(dto, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<MemoryDto> fc(@RequestBody MemoryDto dto){
		Memory mem = convertToEntity(dto);
		mem.setFeeling(Feeling.HAPPY);
		MemoryDto d = convertToDto(mem);
		return new ResponseEntity<MemoryDto>(d, HttpStatus.OK);
	}
}
