package com.example.calendar.Service;

import com.example.calendar.Model.Event;
import com.example.calendar.Model.Participant;

import java.util.List;

public interface ParticipantService {
    List<Event> addEvent(Participant participant, Event event);
}
