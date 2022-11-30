package com.example.calendar.Service;

import com.example.calendar.Model.Event;
import com.example.calendar.Model.Participant;

import java.util.List;

public interface EventService {
    void AddParticipant(Long eventId, String ownerEmail, String participantEmail);

    Event changeEvent(Event event, String email);

    Event addEvent(Event event, String email);

    List<Participant> getParticipant(Long eventId, String email);
}
