package com.example.routine.exception;
@SuppressWarnings("serial")
public class DayNotFoundException extends RuntimeException {
	public DayNotFoundException(Long id) {
	        super("Day is not found, id="+id);
	    }
}
