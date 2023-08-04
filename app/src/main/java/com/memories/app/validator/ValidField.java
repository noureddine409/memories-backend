package com.memories.app.validator;

import com.memories.app.validator.impl.FieldConstraintValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = FieldConstraintValidator.class)
@Target({ TYPE, FIELD, ANNOTATION_TYPE })
@Retention(RUNTIME)
public @interface ValidField {
	
	
	String message() default "field must not be blank" ;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
