package com.memories.app.validator;

import static com.memories.app.commun.CoreConstant.Exception.VALIDATION_NOT_BLANK;
import static com.memories.app.commun.CoreConstant.Exception.VALIDATION_NOT_NULL;

import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.memories.app.dto.CommentDto;

@Component
public class CommentValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return CommentDto.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comment", VALIDATION_NOT_BLANK);
		
		final CommentDto commentDto = (CommentDto) target;
		
		if(Objects.isNull(commentDto.getMemory())) {
			errors.rejectValue("memory", VALIDATION_NOT_NULL);
		}
		else {
			if(Objects.isNull(commentDto.getMemory().getId())) {
				errors.rejectValue("id", VALIDATION_NOT_NULL);
			}
		}
		
	}
	
}
