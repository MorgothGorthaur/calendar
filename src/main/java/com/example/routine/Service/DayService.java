package com.example.routine.Service;

import java.sql.Date;
import java.util.List;

import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;

public interface DayService  {
	public List <Day> findAll();
	public Day findById(Long id);
	//public Long save(Day day);
	public Day save(Day day);
	public void deleteById(Long id);
	public void updateDay(Day day);
	//public void addEvent(Event event);
	public Event addEvent(Event event);
	public DayActuality checkActuality(Date date);
}
