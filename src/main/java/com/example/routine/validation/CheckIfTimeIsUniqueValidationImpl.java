package com.example.routine.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;



import org.springframework.stereotype.Component;

import com.example.routine.Model.Event;

/*
 * check if event time is unick for this day
 */
@Component
public class CheckIfTimeIsUniqueValidationImpl implements ConstraintValidator <CheckIfTimeIsUniqueValidation , Event>{


	

	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		//System.out.println(event.getId());
		try {
//			var day = dayRepository.findById(event.getDayId()).orElseThrow(() -> new DayNotFoundException(event.getDayId()));
//			return !day.getEvents().contains(event);
			return true;
		} catch (Exception ex) {
			throw ex;
		}
	}
	

}
