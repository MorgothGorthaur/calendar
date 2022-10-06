package com.example.routine.validation;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckIfDateIsActualValidationImpl implements ConstraintValidator < CheckIfDateIsActualValidation, LocalDate> {

	@Override
	public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
		try {
			return date.equals(LocalDate.now()) || date.isAfter(LocalDate.now());
		} catch (Exception ex) {		
			return false;
		}
	}

}
