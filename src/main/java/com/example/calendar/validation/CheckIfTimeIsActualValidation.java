package com.example.calendar.validation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
/*
 * prevent non-actual events from being set for today
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CheckIfTimeIsActualValidationImpl.class)
@Documented
public @interface CheckIfTimeIsActualValidation {
	 String message() default "Time must be actual!";
	 Class<?>[] groups() default {};
	 Class<? extends Payload>[] payload() default {};
}
