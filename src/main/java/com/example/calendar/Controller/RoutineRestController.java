package com.example.calendar.Controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;


import com.example.calendar.DTO.EventDto;
import com.example.calendar.DTO.ParticipantDto;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Repository.EventRepository;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.Service.AuthorService;
import com.example.calendar.exception.EventNotFoundException;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/routine")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class RoutineRestController {

    private EventRepository eventRepository;
    private ParticipantRepository participantRepository;
    private ModelMapper modelMapper;
    private AuthorService authorService;
    @GetMapping
    public List<ParticipantDto> findAll() {
        return participantRepository.findParticipantByStatus(ParticipantStatus.ACTIVE).stream().
                map(participant -> modelMapper.map(participant, ParticipantDto.class)).toList();
    }

    @PostMapping
    public ParticipantDto addParticipant(@Valid @RequestBody ParticipantDto participantDto) {
        var participant = participantDto.toParticipant();
        return modelMapper.map(participantRepository.save(participant), ParticipantDto.class);
    }

    @GetMapping("/{participantId}")
    public List<EventDto> getWithEvents(@PathVariable Long participantId) {
        var participant = participantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE).
                orElseThrow(() -> new ParticipantNotFoundException(participantId));
        var events = participant.getEvents().stream().filter(event -> event.getEndTime().isAfter(LocalDateTime.now())).toList();
        return events.stream().map(event -> modelMapper.map(event, EventDto.class)).toList();
    }

    @DeleteMapping("/{participantId}")
    public String deleteParticipant(@PathVariable Long participantId) {
        var participant = participantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE).
                orElseThrow(() -> new ParticipantNotFoundException(participantId));
        participant.setStatus(ParticipantStatus.REMOVED);
        participant.setEvents(null);
        participantRepository.save(participant);
        return "deleted";
    }

    @PatchMapping()
    public ParticipantDto changeParticipant(@Valid @RequestBody ParticipantDto participantDto) {
        var participant = participantRepository.findByIdAndStatus(participantDto.getId(), ParticipantStatus.ACTIVE).
                orElseThrow(() -> new ParticipantNotFoundException(participantDto.getId()));
        participant.setLastName(participantDto.getLastName());
        participant.setFirstName(participantDto.getFirstName());
        participantRepository.save(participant);
        return participantDto;
    }

    @PostMapping("/{participantId}/events")
    public EventDto addEvent(@PathVariable Long participantId, @Valid @RequestBody EventDto eventDto) {

        var participant = participantRepository.findById(participantId).orElseThrow(() -> new ParticipantNotFoundException(participantId));
        var event = eventDto.toEvent();
        var events = authorService.addEvent(participant, event);
        return modelMapper.map(events.get(events.size() -1), EventDto.class);
    }
    @PatchMapping("/{participantId}/events")
    public EventDto changeEvent(@PathVariable Long participantId, @Valid @RequestBody EventDto eventDto) {
        var participant = participantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));
        participant.setEvents(participant.getEvents().stream().filter(event -> !event.getId().equals(eventDto.getId())).toList());
        var event = eventDto.toEvent();
        var events = authorService.addEvent(participant, event);
        return modelMapper.map(events.get(0),EventDto.class);

    }
    @DeleteMapping("/{participantId}/events/{eventId}")
    public String deleteEvent(@PathVariable Long participantId, @PathVariable Long eventId) {
        var participant = participantRepository.findById(participantId).orElseThrow(() -> new ParticipantNotFoundException(participantId));
        var event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        participant.removeEvent(event);
        participantRepository.save(participant);
        return "deleted";
    }

}
