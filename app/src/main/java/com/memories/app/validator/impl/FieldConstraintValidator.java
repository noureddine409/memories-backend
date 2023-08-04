package com.memories.app.validator.impl;

import com.memories.app.validator.ValidField;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class FieldConstraintValidator implements ConstraintValidator<ValidField, String> {
	

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(value == null) return true;
		return !value.isBlank();
	}

}
