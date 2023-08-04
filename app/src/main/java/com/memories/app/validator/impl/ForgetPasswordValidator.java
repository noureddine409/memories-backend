package com.memories.app.validator.impl;


import com.memories.app.dto.ForgetPasswordTokenDto;
import com.memories.app.validator.ValidForgetPasswordToken;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ForgetPasswordValidator implements ConstraintValidator<ValidForgetPasswordToken, ForgetPasswordTokenDto> {
	
	@Override
	public boolean isValid(ForgetPasswordTokenDto value, ConstraintValidatorContext context) {
		if(Objects.isNull(value)) {
			return false;
		}
		
		if(Objects.isNull(value.getToken())) {
			return false;
		}
		
		if(value.getToken().isBlank()) {
			return false;
		}
		if(value.getToken().length() != 64) {
			return false;
		}
		
		return true;
	}

}
