package com.example.calendar.validation;

import java.time.LocalDateTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


import com.example.calendar.dto.EventDto;

/*
 * prevent non-actual events from being set for today
 */

public class CheckIfTimeIsActualValidationImpl implements ConstraintValidator <CheckIfTimeIsActualValidation, EventDto>{


	@Override
	public boolean isValid(EventDto event, ConstraintValidatorContext context) {
		return event.getStartTime() != null && (event.getStartTime().equals(LocalDateTime.now()) || event.getStartTime().isAfter(LocalDateTime.now()));
	}

}
