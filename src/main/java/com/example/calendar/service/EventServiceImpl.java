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
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final ParticipantRepository participantRepository;

    @Override
    public void addParticipant(Long eventId, String ownerEmail, String participantEmail) {
        var event = checkIfParticipantContainsEventById(eventId, ownerEmail);
        var participant = participantRepository.findByEmailAndStatus(participantEmail.strip(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(participantEmail.strip()));
        checkIfParticipantDoesntContainsEvent(event, participantEmail);
        participant.addEvent(event);
        participantRepository.save(participant);
    }

    @Override
    public Event changeEvent(Event event, String email) {
        checkIfParticipantDoesntContainsEvent(event, email);
        checkIfParticipantContainsEventById(event.getId(), email);
        var changed = eventRepository.findById(event.getId()).orElseThrow(() -> new EventNotFoundException(event.getId()));
        changed.setStartTime(event.getStartTime());
        changed.setEndTime(event.getEndTime());
        changed.setDescription(event.getDescription());
        return eventRepository.save(event);
    }

    private Event checkIfParticipantContainsEventById(Long eventId, String email) {
        return eventRepository.checkIfParticipantContainsEventWithId(eventId, email).orElseThrow(() -> new ParticipantDoesntContainsThisEvent(eventId));
    }

    private void checkIfParticipantDoesntContainsEvent(Event event, String email) {
        var check = eventRepository.checkIfParticipantContainsEventWithSameTimeAndDescription(event.getStartTime(), event.getEndTime(), event.getDescription(), email);
        if (check.isPresent() ) {
            throw new ParticipantAlreadyContainsEvent();
        }
    }

    @Override
    public Event addEvent(Event event, String email) {
        checkIfParticipantDoesntContainsEvent(event, email);
        var participant = participantRepository.findByEmailAndStatus(email, ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(email));
        participant.addEvent(event);
        participant = participantRepository.save(participant);
        var events = participant.getEvents().stream().filter(e -> e.equals(event)).toList();
        return events.get(0);
    }

    @Override
    public List<Participant> getParticipant(Long eventId, String email) {
        var event = checkIfParticipantContainsEventById(eventId, email);
        return participantRepository.getParticipantsWithoutThisEmail(eventId, email);
    }
}
