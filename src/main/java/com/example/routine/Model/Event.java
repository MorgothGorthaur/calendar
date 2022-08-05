package com.example.routine.Model;


import java.sql.Time;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.routine.validation.CheckIfTheTimeIsActualValidation;
import com.example.routine.validation.CheckIfTheTimeIsUniqueValidation;





@Entity
@Table(name = "events")
@CheckIfTheTimeIsUniqueValidation 
@CheckIfTheTimeIsActualValidation
public class Event {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	  
	@Column(name = "events_time")
	@NotNull(message = "time must be setted!")
	private Time time;
	private Long dayId;
	@Column(name = "description")
	@NotNull(message = "description must be no null")
	@Size(min=2, max=30, message = "description must have size between 2 and 30 literals")
	public String description;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Time getTime() {
		return time;
	}
	public void setDate(Time time) {
		this.time = time;
	}
	public String getDescription() {
		return description;
	}
	public Long getDayId() {
		return dayId;
	}
	public void setDayId(Long dayId) {
		this.dayId = dayId;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
