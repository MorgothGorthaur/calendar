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
			var day = dayRepository.findDayByDate(dayDto.getDate());
			return day.isEmpty() || day.get().getId().equals(dayDto.getId());
		} catch ( Exception ex) {
			return false;
		}
	}
	

}
