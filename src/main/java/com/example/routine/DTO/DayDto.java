package com.example.routine.DTO;

import java.sql.Date;
import java.util.Objects;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.routine.Model.DayActuality;
import com.example.routine.validation.CheckIfDateIsActualValidation;
import com.example.routine.validation.CheckIfDateIsUniqueValidation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/*
 *  Day class without list of events
 */
@CheckIfDateIsUniqueValidation
@Getter @Setter
@EqualsAndHashCode
public class DayDto {
	@NotNull(message = "name must be no null")
	@Size(min=2, max=30, message = "name must have size between 2 and 30 literals")
	private String name;
	@NotNull(message = "date must be seted!")
	@CheckIfDateIsActualValidation

	private Date date;
	private Long id;
	private DayActuality dayActuality;

}
