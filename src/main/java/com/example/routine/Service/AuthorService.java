package com.example.routine.Service;

import com.example.routine.Model.Event;
import com.example.routine.Model.Participant;

import java.util.List;

public interface AuthorService {
    List<Event> addEvent(Participant participant, Event event);
}
