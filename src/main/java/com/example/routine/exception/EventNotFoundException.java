package com.example.routine.exception;

@SuppressWarnings("serial")
public class EventNotFoundException extends RuntimeException{
	public EventNotFoundException(Long id) {
        super("Event is not found, id="+id);
    }
}
