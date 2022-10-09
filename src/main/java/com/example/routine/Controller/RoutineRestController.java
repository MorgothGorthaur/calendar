package com.example.routine.Controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;


import com.example.routine.DTO.ParticipantDto;
import com.example.routine.DTO.ParticipantFullDto;
import com.example.routine.Model.Participant;
import com.example.routine.Model.ParticipantStatus;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Repository.ParticipantRepository;
import com.example.routine.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.example.routine.Model.Event;


@RestController
@RequestMapping("/routine")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class RoutineRestController {

    private EventRepository eventRepository;
    private ParticipantRepository particioantRepository;
    private ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<ParticipantDto>> findAll() {
        return ResponseEntity.ok(particioantRepository.findParticipantByStatus(ParticipantStatus.ACTIVE).stream().map(participant -> modelMapper.map(participant, ParticipantDto.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<ParticipantDto> addParticipant(@Valid @RequestBody ParticipantDto participantDto) {
        var participant = participantDto.toParticipant();
        return ResponseEntity.ok(modelMapper.map(particioantRepository.save(participant), ParticipantDto.class));
    }

    @GetMapping("/{participantId}")
    public ResponseEntity<ParticipantFullDto> getWithEvents(@PathVariable Long participantId) {
        var participant = particioantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE).orElseThrow(() -> new ParticipantNotFoundException(participantId));
        return ResponseEntity.ok(modelMapper.map(participant, ParticipantFullDto.class));
    }

    @DeleteMapping("/{participantId}")
    public ResponseEntity<String> deleteParticipant(@PathVariable Long participantId){
        var participant = particioantRepository.findByIdAndStatus(participantId, ParticipantStatus.ACTIVE).orElseThrow(() -> new ParticipantNotFoundException(participantId));
        participant.setStatus(ParticipantStatus.REMOVED);
        particioantRepository.save(participant);
        return ResponseEntity.ok("deleted");
    }
    @PatchMapping()
    public ResponseEntity<ParticipantDto> changeParticipant(@Valid @RequestBody ParticipantDto participantDto){
        var participant = particioantRepository.findByIdAndStatus(participantDto.getId(), ParticipantStatus.ACTIVE).orElseThrow(()-> new ParticipantNotFoundException(participantDto.getId()));
        participant.setLastName(participantDto.getLastName());
        participant.setFirstName(participantDto.getFirstName());
        particioantRepository.save(participant);
        return ResponseEntity.ok(participantDto);
    }
    @PostMapping("/events")
    public ResponseEntity<Event> addEvent(@Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventRepository.save(event));
    }

    @DeleteMapping("/events/{eventId}")
    public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) {
        eventRepository.deleteById(eventId);
        return ResponseEntity.ok("deleted!");
    }

    @PatchMapping("/events")
    public ResponseEntity<@Valid Event> changeEvent(@Valid @RequestBody Event event) {
        return ResponseEntity.ok(eventRepository.save(event));
    }
}
