package com.example.routine.Service;


import com.example.routine.Model.Event;

public interface EventService {
	public void deleteById(Long id);
	public void updateEvent(Event event);
	public Event findById(Long id);
}
