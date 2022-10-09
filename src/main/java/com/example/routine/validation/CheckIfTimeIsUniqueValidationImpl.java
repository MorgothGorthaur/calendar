package com.example.routine.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.routine.Repository.DayRepository;
import com.example.routine.exception.DayNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.routine.Model.Event;

/*
 * check if event time is unick for this day
 */
@AllArgsConstructor
@Component
public class CheckIfTimeIsUniqueValidationImpl implements ConstraintValidator <CheckIfTimeIsUniqueValidation , Event>{

	private DayRepository dayRepository;
	

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
