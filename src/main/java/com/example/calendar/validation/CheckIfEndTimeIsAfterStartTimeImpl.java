package com.example.calendar.validation;

import com.example.calendar.dto.EventDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class CheckIfEndTimeIsAfterStartTimeImpl implements ConstraintValidator<CheckIfEndTimeIsAfterStartTime, EventDto> {
    @Override
    public boolean isValid(EventDto event, ConstraintValidatorContext context) {
        return event.getStartTime() != null && event.getEndTime() != null && event.getEndTime().isAfter(event.getStartTime());
    }
}
