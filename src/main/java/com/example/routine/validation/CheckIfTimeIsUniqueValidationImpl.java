package com.example.routine.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

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
	private DayService dayService;
	

	@Override
	public boolean isValid(Event event, ConstraintValidatorContext context) {
		//System.out.println(event.getId());
		try {
		
			for (Event e : dayService.findById(event.getDayId()).getEvents()) {
				if ( e.getTime().toLocalTime().compareTo(event.getTime().toLocalTime()) == 0){
				    if(event.getId() == e.getId()) {
				    	return true;
				    }
				    return false;
					
				}
				
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			System.out.println("error");
			return false;
		}
		return true;
	}
	

}
