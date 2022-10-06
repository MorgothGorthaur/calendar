package com.example.routine.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.routine.Repository.DayRepository;
import com.example.routine.exception.DayNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.routine.Model.Event;
import com.example.routine.Service.DayService;

/*
 * check if event time is unick for this day
 */
@Component
public class CheckIfTimeIsUniqueValidationImpl implements ConstraintValidator <CheckIfTimeIsUniqueValidation , Event>{
	@Autowired
	private DayRepository dayRepository;
	

	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		//System.out.println(event.getId());
		try {
			var day = dayRepository.findById(event.getDayId()).orElseThrow(() -> new DayNotFoundException(event.getDayId()));
			return day.getEvents().contains(event);

		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("error");
			return false;
		}
	}
	

}
