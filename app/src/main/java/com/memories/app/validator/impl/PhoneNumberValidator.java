package com.memories.app.validator.impl;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;
import com.memories.app.dto.PhoneNumberDto;
import com.memories.app.validator.ValidPhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.util.Objects;


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
        if (phoneNumber == null) return true;
        else {
            if (Objects.isNull(phoneNumber.getRegion()) || Objects.isNull(phoneNumber.getPhoneNumber())) {
                return false;
            }
        }
        PhoneNumber number = null;

        try {

            number = phoneUtil.parse(phoneNumber.getPhoneNumber(), phoneNumber.getRegion().name());

        } catch (NumberParseException e) {
            log.info("Unable to parse the given phone number: " + phoneNumber);
        }


        return phoneUtil.isValidNumber(number);
    }

}
