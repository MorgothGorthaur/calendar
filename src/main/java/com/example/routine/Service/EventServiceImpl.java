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
        var events = eventRepository.findAll();
        for(var ev : events){
            if(ev.getEndTime().equals(event.getEndTime()) && ev.getDescription().equals(event.getDescription()) && ev.getStartTime().equals(event.getStartTime())){
                event.setId(ev.getId());
            }
        }
    }
}
