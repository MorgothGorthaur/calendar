package com.example.calendar.exception;

public class ParticipantAlreadyContainsEvent extends RuntimeException{
    public ParticipantAlreadyContainsEvent(){
        super("participant already contains event!");
    }
}
