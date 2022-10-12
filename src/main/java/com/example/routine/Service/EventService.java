package com.example.routine.Service;

import com.example.routine.Model.Event;
import org.springframework.stereotype.Service;

public interface EventService {
    void checkIfEventUniq(Event event);
}
