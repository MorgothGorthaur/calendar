package com.example.calendar.Controller;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;


import com.example.calendar.DTO.EventDto;
import com.example.calendar.DTO.ParticipantDto;
import com.example.calendar.DTO.ParticipantFullDto;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Repository.EventRepository;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.exception.EmailNotUnique;
import com.example.calendar.exception.EventNotFoundException;
import com.example.calendar.exception.ParticipantAlreadyContainsEvent;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/calendar/user")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ParticipantRestController {

    private EventRepository eventRepository;
    private ParticipantRepository participantRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder encoder;

    @GetMapping
    public List<ParticipantDto> findAll() {
        return participantRepository.findParticipantByStatus(ParticipantStatus.ACTIVE).stream().
                map(participant -> modelMapper.map(participant, ParticipantDto.class)).toList();
    }


    @PostMapping
    public ParticipantDto addParticipant(@Valid @RequestBody ParticipantFullDto dto) {
        var participant = dto.toParticipant();
        participant.setPassword(encoder.encode(participant.getPassword()));
        if (participantRepository.findParticipantsWithEqualEmailAndNonEqualId(participant.getEmail(), 0L).size() != 0) {
            throw new EmailNotUnique(participant.getEmail());
        }
        return modelMapper.map(participantRepository.save(participant), ParticipantDto.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/events")
    public List<EventDto> getWithEvents(Principal principal) {
        System.out.println("???");
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));

        return participant.getEvents().stream().filter(event -> event.getEndTime().isAfter(LocalDateTime.now())).map(event -> modelMapper.map(event, EventDto.class)).toList();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    public String deleteParticipant(Principal principal) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setStatus(ParticipantStatus.REMOVED);
        participantRepository.save(participant);
        SecurityContextHolder.getContext().setAuthentication(null);
        return "deleted";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ParticipantDto changeParticipant(Principal principal, @Valid @RequestBody ParticipantFullDto dto) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setFirstName(dto.getFirstName());
        participant.setLastName(dto.getLastName());
        participant.setEmail(dto.getEmail());
        participant.setPassword(encoder.encode(dto.getPassword()));
        if (participantRepository.findParticipantsWithEqualEmailAndNonEqualId(participant.getEmail(), participant.getId()).size() != 0) {
            throw new EmailNotUnique(participant.getEmail());
        }
        participantRepository.save(participant);
        return dto;
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/events")
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
    @PostMapping("/events/{eventId}")
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
    @PatchMapping("/events")
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
    @DeleteMapping("/events/{eventId}")
    public String deleteEvent(Principal principal, @PathVariable Long eventId) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        var event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        participant.removeEvent(event);
        participantRepository.save(participant);
        return "deleted";
    }

}
