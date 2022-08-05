package com.example.routine.Service;

import java.sql.Date;
import java.util.List;

import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;

public interface DayService  {
	public List <Day> findAll();
	public Day findById(Long id);
	public Long save(Day day);
	public void daleteById(Long id);
	public void updateDay(Day day);
	public void addEvent(Event event);
	public DayActuality checkActuality(Date date);
}
