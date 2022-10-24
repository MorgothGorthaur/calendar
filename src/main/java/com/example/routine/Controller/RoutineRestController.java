package com.example.routine.Controller;

import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;


import com.example.routine.DTO.EventDto;
import com.example.routine.DTO.ParticipantDto;
import com.example.routine.Model.ParticipantStatus;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Repository.ParticipantRepository;
import com.example.routine.Service.EventService;
import com.example.routine.exception.EventNotFoundException;
import com.example.routine.exception.ParticipantAlreadyContainsEvent;
import com.example.routine.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/routine")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class RoutineRestController {

    private EventRepository eventRepository;
    private ParticipantRepository participantRepository;
    private ModelMapper modelMapper;
    private EventService eventService;
    @GetMapping
    public ResponseEntity<List<ParticipantDto>> findAll() {
        return ResponseEntity.ok(participantRepository.findParticipantByStatus(ParticipantStatus.ACTIVE).stream().
                map(participant -> modelMapper.map(participant, ParticipantDto.class)).toList());
    }

    @PostMapping
    public ResponseEntity<ParticipantDto> addParticipant(@Valid @RequestBody ParticipantDto participantDto) {
        var participant = participantDto.toParticipant();
        return ResponseEntity.ok(modelMapper.map(participantRepository.save(participant), ParticipantDto.class));
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<List<EventDto>> getWithEvents(@PathVariable Long participantId) {
        var participant = participantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE).
                orElseThrow(() -> new ParticipantNotFoundException(participantId));
        var events = participant.getEvents().stream().filter(event -> event.getEndTime().isAfter(LocalDateTime.now())).toList();
        return ResponseEntity.ok(events.stream().map(event -> modelMapper.map(event, EventDto.class)).toList());
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long participantId) {
        var participant = participantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE).
                orElseThrow(() -> new ParticipantNotFoundException(participantId));
        participant.setStatus(ParticipantStatus.REMOVED);
        participant.setEvents(null);
        participantRepository.save(participant);
        return ResponseEntity.ok("deleted");
    }

    @PatchMapping()
    public ResponseEntity<ParticipantDto> changeParticipant(@Valid @RequestBody ParticipantDto participantDto) {
        var participant = participantRepository.findByIdAndStatus(participantDto.getId(), ParticipantStatus.ACTIVE).
                orElseThrow(() -> new ParticipantNotFoundException(participantDto.getId()));
        participant.setLastName(participantDto.getLastName());
        participant.setFirstName(participantDto.getFirstName());
        participantRepository.save(participant);
        return ResponseEntity.ok(participantDto);
    }

    @PostMapping("/{participantId}/events")
    public ResponseEntity<EventDto> addEvent(@PathVariable Long participantId, @Valid @RequestBody EventDto eventDto) {
        var event = eventDto.toEvent();
        eventService.checkIfEventUniq(event);
        var participant = participantRepository.findById(participantId).orElseThrow(() -> new ParticipantNotFoundException(participantId));
        if(participant.getEvents() != null && participant.getEvents().contains(event)){
            throw new ParticipantAlreadyContainsEvent();
        }
        participant.addEvent(event);
        var events = participantRepository.save(participant).getEvents();
        return ResponseEntity.ok(modelMapper.map(events.get(events.size() -1), EventDto.class));
    }
    @PatchMapping("/{participantId}/events")
    public ResponseEntity<@Valid EventDto> changeEvent(@PathVariable Long participantId, @Valid @RequestBody EventDto eventDto) {
        var participant = participantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(participantId));
        participant.setEvents(participant.getEvents().stream().filter(event -> !event.getId().equals(eventDto.getId())).toList());
        var event = eventDto.toEvent();
        eventService.checkIfEventUniq(event);
        if(participant.getEvents().contains(event)){
            throw new ParticipantAlreadyContainsEvent();
        }
        participant.addEvent(event);
        var events = participantRepository.save(participant).getEvents().stream().filter(e -> e.equals(event)).toList();
        return ResponseEntity.ok(modelMapper.map(events.get(0),EventDto.class));

    }
    @DeleteMapping("/{participantId}/events/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long participantId, @PathVariable Long eventId) {
        var participant = participantRepository.findById(participantId).orElseThrow(() -> new ParticipantNotFoundException(participantId));
        //var events = participant.getEvents().stream().filter(event -> !event.getId().equals(eventId)).toList();
        var event = eventRepository.findById(eventId).orElseThrow(() -> new EventNotFoundException(eventId));
        participant.removeEvent(event);
        participantRepository.save(participant);
        return ResponseEntity.ok("deleted");
    }

}
