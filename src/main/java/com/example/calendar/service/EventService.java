package com.example.calendar.service;

import com.example.calendar.model.Event;
import com.example.calendar.model.Participant;

import java.util.List;

public interface EventService {
    void addParticipant(Long eventId, String ownerEmail, String participantEmail);

    Event changeEvent(Event event, String email);

    Event addEvent(Event event, String email);

    List<Participant> getParticipant(Long eventId, String email);
}
