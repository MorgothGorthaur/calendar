package com.example.routine.validation;

import java.sql.Time;
import java.time.LocalTime;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Service.DayService;
/*
 * prevent non-actual events from being set for today
 */
public class CheckIfTimeIsActualValidationImpl implements ConstraintValidator <CheckIfTimeIsActualValidation, Event>{
	@Autowired
	private DayService dayService;
	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		try {
			return event.getTime().equals(LocalTime.now()) || event.getTime().isAfter(LocalTime.now());
		} catch ( Exception ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}

}
