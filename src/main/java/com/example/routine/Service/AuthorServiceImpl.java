package com.example.routine.Service;

import com.example.routine.Model.Event;
import com.example.routine.Model.Participant;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Repository.ParticipantRepository;
import com.example.routine.exception.ParticipantAlreadyContainsEvent;
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
