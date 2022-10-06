package com.example.routine.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.example.routine.Repository.DayRepository;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.routine.DTO.DayDto;


public class CheckIfDateIsUniqueValidationImpl implements ConstraintValidator <CheckIfDateIsUniqueValidation, DayDto>{
	@Autowired
	private DayRepository dayRepository;
	@Override
	public boolean isValid(DayDto dayDto, ConstraintValidatorContext context) {
		try {
			return dayRepository.findDayByDate(dayDto.getDate()).isEmpty();
		} catch ( Exception ex) {
			return false;
		}
	}
	

}
