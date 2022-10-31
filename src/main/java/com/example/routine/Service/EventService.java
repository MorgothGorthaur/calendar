package com.example.routine.Service;

import com.example.routine.Model.Event;
import com.example.routine.Model.Participant;
import org.springframework.stereotype.Service;

import java.util.List;

public interface EventService {
    List<Event> addEvent(Participant participant, Event event);
}
