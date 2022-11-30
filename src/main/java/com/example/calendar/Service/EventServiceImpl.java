package com.example.calendar.Service;

import com.example.calendar.Model.Event;
import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Repository.EventRepository;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.exception.EventNotFoundException;
import com.example.calendar.exception.ParticipantAlreadyContainsEvent;
import com.example.calendar.exception.ParticipantDoesntContainsThisEvent;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EventServiceImpl implements EventService {
    private EventRepository eventRepository;
    private ParticipantRepository participantRepository;

    @Override
    public void AddParticipant(Long eventId, String ownerEmail, String participantEmail) {
        var event = checkIfParticipantDoesntContainsEvent(eventId, ownerEmail);
        var participant = participantRepository.findByEmailAndStatus(participantEmail.strip(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(participantEmail.strip()));

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
        var participant = participantRepository.findByEmail(email)
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
