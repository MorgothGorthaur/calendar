package com.example.routine.validation;

import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.routine.Repository.DayRepository;
import com.example.routine.exception.DayNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;

/*
 * prevent non-actual events from being set for today
 */
@AllArgsConstructor
public class CheckIfTimeIsActualValidationImpl implements ConstraintValidator <CheckIfTimeIsActualValidation, Event>{

	private DayRepository dayRepository;
	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		try {
//			var day = dayRepository.findById(event.getDayId()).orElseThrow(() -> new DayNotFoundException(event.getDayId()));
//			if(day.getDayActuality().equals(DayActuality.TODAY)){
//				return event.getTime().equals(LocalTime.now()) || event.getTime().isAfter(LocalTime.now());
//			}
			return true;
		} catch ( Exception ex) {
			return false;
		}
	}

}
