package com.memories.app.validator;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.memories.app.validator.impl.ForgetPasswordValidator;


@Documented
@Constraint(validatedBy = ForgetPasswordValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidForgetPasswordToken {
	String message() default "Invalid token";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
