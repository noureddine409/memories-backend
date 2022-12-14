package com.memories.app.controller;


import static com.memories.app.model.GenericEnum.MediaType.PICTURE;
import static com.memories.app.model.GenericEnum.MediaType.VIDEO;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.memories.app.commun.CoreConstant;
import com.memories.app.dto.MemoryDto;
import com.memories.app.exception.BusinessException;
import com.memories.app.exception.ElementNotFoundException;
import com.memories.app.exception.UnauthorizedFileFormatException;
import com.memories.app.model.Media;
import com.memories.app.model.Memory;
import com.memories.app.model.User;
import com.memories.app.service.AwsS3Service;
import com.memories.app.service.MemoriesService;
@RestController
@RequestMapping("api/memories")
public class MemoriesController extends GenericController<Memory, MemoryDto> {
	
	@Autowired
	private MemoriesService memoriesService;
	@Autowired
    private AwsS3Service awsS3Service;

	
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
	@PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	ResponseEntity<MemoryDto> saveMemorie(@RequestPart(value ="memory", required = true) MemoryDto memoryDto,
			@RequestPart(value="files", required = false) MultipartFile[] multipartFiles) throws BusinessException{
		final Memory entity = convertToEntity(memoryDto);
		final User currentUser = getCurrentUser();	
		ArrayList<Media> list = new ArrayList<Media>();
		entity.setCreatedBy(currentUser);
		if(multipartFiles != null) {
			final int mediasnumber = multipartFiles.length ;
			if(mediasnumber > 10)
				throw new BusinessException(null, new UnauthorizedFileFormatException(), CoreConstant.Exception.UNAUTHORIZIED_FILE_NUMBER, new Object[]{});
			for(int i=0; i<mediasnumber; i++) {
				final String type = multipartFiles[i].getContentType();
				if(!(type.contains("video") || type.contains("image")))
					throw new UnauthorizedFileFormatException(null, new UnauthorizedFileFormatException(), CoreConstant.Exception.FILE_UNAUTHORIZED_FORMAT, new Object[]{});
				Media media = new Media();
				String path = awsS3Service.save(multipartFiles[i]);
				media.setPath(path);
				media.setCreatedAt(media.getCreatedAt());
				if(type.contains("image")) {
					media.setType(PICTURE);
				}
				if(type.contains("video")) {
					media.setType(VIDEO);
				}
				list.add(media);
				entity.setMedias(list);
			}
		}		
		
		Memory saved = memoriesService.save(entity);
		return new ResponseEntity<MemoryDto>(convertToDto(saved), HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	ResponseEntity<Boolean> deleteMemorie(@PathVariable Long id) throws ElementNotFoundException {	
		final Memory memoryFound = memoriesService.findById(id);
		if(isOwner(getCurrentUser(), memoryFound))  {
			final List<Media> medias = memoryFound.getMedias();
			if(!medias.isEmpty())
				medias.stream()
					.forEach(media -> awsS3Service.delete(media.getMediaName()));
				
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
	
	@PostMapping("/loves/{id}")
	public ResponseEntity<Boolean> love(@PathVariable Long id) {
		memoriesService.findById(id);
		final Long currentUserId = getCurrentUserId();
		if(memoriesService.loveExists(currentUserId, id)) {
			return new ResponseEntity<Boolean>(memoriesService.deleteLove(currentUserId, id), HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(memoriesService.addLove(currentUserId, id), HttpStatus.OK);
	}
	
	private Boolean isOwner(User currentUser, Memory memory) {
		if(currentUser.getEmail().equals(memory.getCreatedBy().getEmail())) {
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}
		
}
