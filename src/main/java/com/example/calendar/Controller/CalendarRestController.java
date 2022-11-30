package com.example.calendar.Controller;

import java.security.Principal;
import java.util.List;

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
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CalendarRestController {

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
    @GetMapping("/user/events")
    public List<EventDto> getWithEvents(Principal principal) {
        System.out.println("???");
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));

        return participant.getEvents().stream().map(event -> modelMapper.map(event, EventDto.class)).toList();
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/user")
    public String deleteParticipant(Principal principal) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setStatus(ParticipantStatus.REMOVED);
        participantRepository.save(participant);
        SecurityContextHolder.getContext().setAuthentication(null);
        return "deleted";
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/user")
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
    @PostMapping("/user/events")
    public EventDto addEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {

        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        var event = eventDto.toEvent();
        if(participant.getEvents() != null && participant.getEvents().contains(event)){
            throw new ParticipantAlreadyContainsEvent();
        }
        participant.addEvent(event);
        return modelMapper.map(participant.getEvents().get(participant.getEvents().size() - 1), EventDto.class);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping("/user/events")
    public EventDto changeEvent(Principal principal, @Valid @RequestBody EventDto eventDto) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setEvents(participant.getEvents().stream().filter(event -> !event.getId().equals(eventDto.getId())).toList());
        var event = eventDto.toEvent();
        if(participant.getEvents() != null && participant.getEvents().contains(event)){
            throw new ParticipantAlreadyContainsEvent();
        }
        participant.addEvent(event);
        return modelMapper.map(participant.getEvents().get(0), EventDto.class);

    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/user/events/{eventId}")
    public String deleteEvent(Principal principal, @PathVariable Long eventId) {
        var participant = participantRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        var event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        participant.removeEvent(event);
        participantRepository.save(participant);
        return "deleted";
    }

}
