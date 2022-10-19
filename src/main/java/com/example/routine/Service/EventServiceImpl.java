package com.example.routine.Service;

import com.example.routine.Model.Event;
import com.example.routine.Repository.EventRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService{
    private EventRepository eventRepository;
    @Override
    public void checkIfEventUniq(Event event) {
        var events = eventRepository.findAll().stream().filter(e -> e.getParticipants() != null).toList();
        for(var ev : events){
            if(ev.equals(event)){
                event.setId(ev.getId());
            }
        }
    }
}
