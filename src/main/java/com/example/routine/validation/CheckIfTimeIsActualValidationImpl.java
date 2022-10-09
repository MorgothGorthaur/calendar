package com.example.routine.validation;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



import com.example.routine.Model.Event;

/*
 * prevent non-actual events from being set for today
 */

public class CheckIfTimeIsActualValidationImpl implements ConstraintValidator <CheckIfTimeIsActualValidation, Event>{


	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		try {
			return event.getStartTime().equals(LocalDateTime.now()) || event.getStartTime().isBefore(LocalDateTime.now());
		} catch ( Exception ex) {
			return false;
		}
	}

}
