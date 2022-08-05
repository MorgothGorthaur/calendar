package com.example.routine.validation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.RetentionPolicy;
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckIfTheDateIsUniqueValidationImpl.class)
public @interface CheckIfTheDateIsUniqueValidation {
	 String message() default "date error! A day has already been set for this date!";
	 Class<?>[] groups() default {};
	 Class<? extends Payload>[] payload() default {};
}
