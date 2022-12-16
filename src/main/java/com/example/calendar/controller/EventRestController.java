package com.example.calendar.controller;

import com.example.calendar.dto.EmailDto;
import com.example.calendar.dto.EventDto;
import com.example.calendar.dto.ParticipantDto;
import com.example.calendar.model.ParticipantStatus;
import com.example.calendar.repository.EventRepository;
import com.example.calendar.repository.ParticipantRepository;
import com.example.calendar.service.EventService;
import com.example.calendar.exception.EventNotFoundException;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/calendar/events")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class EventRestController {
    private final ParticipantRepository participantRepository;
    private final EventRepository eventRepository;
    private final ModelMapper modelMapper;
    private final EventService eventService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public List<EventDto> getWithEvents(Principal principal) {
        System.out.println("1");
        return eventRepository.getEventsByEmail(principal.getName()).stream().filter(event -> event.getEndTime().isAfter(LocalDateTime.now())).map(event -> modelMapper.map(event, EventDto.class)).toList();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public EventDto addEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {
        var event = eventService.addEvent(eventDto.toEvent(), principal.getName());
        return modelMapper.map(event, EventDto.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/{eventId}")
    public void addParticipant(Principal principal, @PathVariable Long eventId, @Valid @RequestBody EmailDto dto) {
        eventService.addParticipant(eventId, principal.getName(), dto.getEmail());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public EventDto changeEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {
        var event = eventService.changeEvent(eventDto.toEvent(), principal.getName());
        return modelMapper.map(event, EventDto.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{eventId}")
    public void deleteEvent(Principal principal, @PathVariable Long eventId) {
        var participant = participantRepository.findByEmailAndStatus(principal.getName(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        var event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        participant.removeEvent(event);
        participantRepository.save(participant);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/users/{eventId}")
    public List<ParticipantDto> getParticipants(Principal principal, @PathVariable Long eventId) {
        var participants = eventService.getParticipant(eventId, principal.getName());
        return participants.stream().map(participant -> modelMapper.map(participant, ParticipantDto.class)).toList();
    }
}
