package com.example.calendar.service;

import com.example.calendar.model.Event;
import com.example.calendar.model.Participant;
import com.example.calendar.model.ParticipantStatus;
import com.example.calendar.repository.EventRepository;
import com.example.calendar.repository.ParticipantRepository;
import com.example.calendar.exception.EventNotFoundException;
import com.example.calendar.exception.ParticipantAlreadyContainsEvent;
import com.example.calendar.exception.ParticipantDoesntContainsThisEvent;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public void AddParticipant(Long eventId, String ownerEmail, String participantEmail) {
        var event = checkIfParticipantDoesntContainsEvent(eventId, ownerEmail);
        var participant = participantRepository.findByEmailAndStatus(participantEmail.strip(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(participantEmail.strip()));
        checkIfParticipantAlreadyContainsEvent(event, participantEmail);
        participant.addEvent(event);
        participantRepository.save(participant);
    }

    @Override
    public Event changeEvent(Event event, String email) {
        checkIfParticipantAlreadyContainsEvent(event, email);
        var changed = eventRepository.findById(event.getId()).orElseThrow(() -> new EventNotFoundException(event.getId()));
        changed.setStartTime(event.getStartTime());
        changed.setEndTime(event.getEndTime());
        changed.setDescription(event.getDescription());
        return eventRepository.save(event);
    }

    private Event checkIfParticipantDoesntContainsEvent(Long eventId, String email) {
        return eventRepository.checkIfParticipantContainsEventWithId(eventId, email).orElseThrow(() -> new ParticipantDoesntContainsThisEvent(eventId));
    }

    private void checkIfParticipantAlreadyContainsEvent(Event event, String email) {
        var check = eventRepository.checkIfParticipantContainsEventWithSameTimeAndDescription(event.getStartTime(), event.getEndTime(), event.getDescription(), email);
        if (check.isPresent() ) {
            throw new ParticipantAlreadyContainsEvent();
        }
    }

    @Override
    public Event addEvent(Event event, String email) {
        checkIfParticipantAlreadyContainsEvent(event, email);
        var participant = participantRepository.findByEmailAndStatus(email, ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(email));
        participant.addEvent(event);
        participant = participantRepository.save(participant);
        return new LinkedList<>(participant.getEvents()).getLast();
    }

    @Override
    public List<Participant> getParticipant(Long eventId, String email) {
        var event = checkIfParticipantDoesntContainsEvent(eventId, email);
        return event.getParticipants();
    }


}
