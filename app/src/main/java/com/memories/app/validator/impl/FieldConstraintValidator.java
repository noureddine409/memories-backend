package com.memories.app.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.memories.app.validator.ValidField;

public class FieldConstraintValidator implements ConstraintValidator<ValidField, String>{
	

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) return true;
		if(value.isBlank()) {
			return false;
		}
		return true;
	}

}
