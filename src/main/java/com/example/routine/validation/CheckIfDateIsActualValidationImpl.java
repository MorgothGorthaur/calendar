package com.example.routine.validation;

import java.sql.Date;
import java.util.Calendar;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckIfDateIsActualValidationImpl implements ConstraintValidator < CheckIfDateIsActualValidation, Date> {

	@Override
	public boolean isValid(Date date, ConstraintValidatorContext context) {
		try {
			Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
			if(currentDate.toLocalDate().compareTo(date.toLocalDate()) >= 1) {
				return false;
			} else {
				return true;
			}
		} catch (Exception ex) {		
			return false;
		}
	}

}
