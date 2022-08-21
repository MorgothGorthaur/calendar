package com.example.routine.validation;

import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.routine.DTO.DayDto;
import com.example.routine.Model.Day;
import com.example.routine.Service.DayService;

public class CheckIfDateIsUniqueValidationImpl implements ConstraintValidator <CheckIfDateIsUniqueValidation, DayDto>{
	@Autowired
	private DayService dayService;
	@Override
	public boolean isValid(DayDto dayDto, ConstraintValidatorContext context) {
		try {
			List<Day> dates = dayService.findAll();
			
			for (Day d :dates) {
				if (d.getDate().toLocalDate().compareTo(dayDto.getDate().toLocalDate()) == 0 && d.getId() != dayDto.getId()) {
					return false;
				}
				
			}
		return true;
		} catch ( Exception ex) {
			return false;
		}
	}
	

}
