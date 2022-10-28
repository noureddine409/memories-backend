package com.memories.app.validator.impl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.memories.app.dto.PhoneNumberDto;
import com.memories.app.validator.ValidPhoneNumber;

import lombok.extern.slf4j.Slf4j;


@Slf4j
public class PhoneNumberValidator implements 
ConstraintValidator<ValidPhoneNumber, PhoneNumberDto> {
	
	PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
	
	
	@Override
	public void initialize(ValidPhoneNumber validPhoneNumber) {
	}
	
	@Override
	public boolean isValid(PhoneNumberDto phoneNumber,
	  ConstraintValidatorContext cxt) {
		
		PhoneNumber number = null;
		
		 try {
		
		number = phoneUtil.parse(phoneNumber.getPhoneNumber(), phoneNumber.getRegion().name());
		
		 }
		 catch (NumberParseException e) {
			 log.info("Unable to parse the given phone number: " + phoneNumber);
		 }
		
		
	    return phoneUtil.isValidNumber(number);
	}

}