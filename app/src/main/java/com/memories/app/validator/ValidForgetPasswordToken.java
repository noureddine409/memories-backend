package com.memories.app.validator;

import com.memories.app.validator.impl.ForgetPasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;


@Documented
@Constraint(validatedBy = ForgetPasswordValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidForgetPasswordToken {
	String message() default "Invalid token";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
