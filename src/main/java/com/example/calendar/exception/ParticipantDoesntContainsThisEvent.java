package com.example.calendar.exception;

public class ParticipantDoesntContainsThisEvent extends RuntimeException {
    public  ParticipantDoesntContainsThisEvent(Long id){
        super("participant doesn't contains event with id " + id);
    }
}
