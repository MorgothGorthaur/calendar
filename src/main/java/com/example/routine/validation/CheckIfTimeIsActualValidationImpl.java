package com.example.routine.validation;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


import com.example.routine.DTO.EventDto;

/*
 * prevent non-actual events from being set for today
 */

public class CheckIfTimeIsActualValidationImpl implements ConstraintValidator <CheckIfTimeIsActualValidation, EventDto>{


	@Override
	public boolean isValid(EventDto event, ConstraintValidatorContext context) {
		try {
			return event.getStartTime().equals(LocalDateTime.now()) || event.getStartTime().isAfter(LocalDateTime.now());
		} catch ( Exception ex) {
			throw ex;
		}
	}

}
