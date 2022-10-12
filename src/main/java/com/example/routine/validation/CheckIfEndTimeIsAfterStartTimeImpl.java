package com.example.routine.validation;

import com.example.routine.DTO.EventDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CheckIfEndTimeIsAfterStartTimeImpl implements ConstraintValidator<CheckIfEndTimeIsAfterStartTime, EventDto> {
    @Override
    public boolean isValid(EventDto event, ConstraintValidatorContext context) {
        try {
            return event.getEndTime().isAfter(event.getStartTime());
        } catch (Exception ex){
            throw ex;
        }
    }
}
