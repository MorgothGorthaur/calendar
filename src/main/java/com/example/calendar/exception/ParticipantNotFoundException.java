package com.example.calendar.exception;

@SuppressWarnings("serial")
public class ParticipantNotFoundException extends RuntimeException {
    public ParticipantNotFoundException(Long id) {
        super("Participant is not found, id=" + id);
    }

    public ParticipantNotFoundException(String email) {
        super("Participant is not found, email= " + email);
    }
}
