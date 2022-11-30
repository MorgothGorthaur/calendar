package com.example.calendar.Controller;

import com.example.calendar.DTO.EventDto;
import com.example.calendar.DTO.ParticipantDto;
import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Repository.EventRepository;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.exception.EventNotFoundException;
import com.example.calendar.exception.ParticipantAlreadyContainsEvent;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/calendar/events")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class EventRestController {
    private ParticipantRepository participantRepository;
    private EventRepository eventRepository;
    private ModelMapper modelMapper;
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<EventDto> getWithEvents(Principal principal) {
        System.out.println("???");
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));

        return participant.getEvents().stream().filter(event -> event.getEndTime().isAfter(LocalDateTime.now())).map(event -> modelMapper.map(event, EventDto.class)).toList();
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public EventDto addEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {

        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        var event = eventDto.toEvent();
        if (participant.getEvents() != null && participant.getEvents().contains(event)) {
            throw new ParticipantAlreadyContainsEvent();
        }
        participant.addEvent(event);
        participant = participantRepository.save(participant);
        return modelMapper.map(new LinkedList<>(participant.getEvents()).getLast(), EventDto.class);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{eventId}")
    public void addParticipant(Principal principal, @PathVariable Long eventId, @RequestBody String email){
        var events = new LinkedList<>(eventRepository.checkIfParticipantContainsEvent(eventId, principal.getName()));
        if(events.size() == 0){
            throw new RuntimeException();
        }
        var participant = participantRepository.findByEmailAndStatus(email.strip(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(email));

        participant.addEvent(events.getFirst());
        participantRepository.save(participant);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public EventDto changeEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setEvents(participant.getEvents().stream()
                .filter(event -> !event.getId().equals(eventDto.getId()))
                .collect(Collectors.toCollection(LinkedList::new)));
        var event = eventDto.toEvent();
        if (participant.getEvents() != null && participant.getEvents().contains(event)) {
            throw new ParticipantAlreadyContainsEvent();
        }
        participant.addEvent(event);
        participant = participantRepository.save(participant);
        return modelMapper.map(new LinkedList<>(participant.getEvents()).getLast(), EventDto.class);

    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{eventId}")
    public String deleteEvent(Principal principal, @PathVariable Long eventId) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        var event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        participant.removeEvent(event);
        participantRepository.save(participant);
        return "deleted";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping ("/users/{eventId}")
    public List<ParticipantDto> getParticipants(Principal principal, @PathVariable Long eventId){
        var events = new LinkedList<>(eventRepository.checkIfParticipantContainsEvent(eventId, principal.getName()));
        System.out.println(principal.getName());
        if(events.size() == 0){
            throw new RuntimeException();
        }
        return events.getFirst().getParticipants().stream().map(participant -> modelMapper.map(participant, ParticipantDto.class)).toList();
    }
}
