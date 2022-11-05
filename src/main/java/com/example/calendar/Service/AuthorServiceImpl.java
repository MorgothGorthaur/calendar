package com.example.calendar.Service;

import com.example.calendar.Model.Event;
import com.example.calendar.Model.Participant;
import com.example.calendar.Repository.EventRepository;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.exception.ParticipantAlreadyContainsEvent;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private EventRepository eventRepository;
    private ParticipantRepository participantRepository;

    private void checkIfEventUniq(Event event) {
        var events = eventRepository.findAll().stream().filter(e -> e.getParticipants() != null).toList();
        for(var ev : events){
            if(ev.equals(event)){
                event.setId(ev.getId());
            }
        }
    }

    @Override
    public List<Event> addEvent(Participant participant, Event event) {
        checkIfEventUniq(event);
        if(participant.getEvents() != null && participant.getEvents().contains(event)){
            throw new ParticipantAlreadyContainsEvent();
        }
        participant.addEvent(event);
        return participantRepository.save(participant).getEvents();
    }
}
