package com.example.routine.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy =  CheckIfEndTimeIsAfterStartTimeImpl.class)
@Documented
public @interface CheckIfEndTimeIsAfterStartTime {
    String message() default "End Time must be after first time!";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
