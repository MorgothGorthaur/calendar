package com.example.routine.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/*
 * check if event time is unick for this day
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckIfTimeIsUniqueValidationImpl.class)
@Documented

public @interface CheckIfTimeIsUniqueValidation {
	 String message() default "an Event has already been set for this day/time";
	 Class<?>[] groups() default {};
	 Class<? extends Payload>[] payload() default {};
}
