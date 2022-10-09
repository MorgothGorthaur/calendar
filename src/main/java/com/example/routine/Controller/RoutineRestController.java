package com.example.routine.Controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;


import com.example.routine.DTO.ParticipantDto;
import com.example.routine.Model.Participant;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Repository.ParticipantRepository;
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
    private ParticipantRepository  particioantRepository;
    private ModelMapper modelMapper;
    @GetMapping
    public ResponseEntity<List<ParticipantDto>> findAll(){
        return ResponseEntity.ok(particioantRepository.findAll().stream().map(participant -> modelMapper.map(participant, ParticipantDto.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Participant> addParticipant(@Valid @RequestBody ParticipantDto participantDto){
        var participant = participantDto.toParticipant();
        return ResponseEntity.ok(particioantRepository.save(participant));
    }

    /*@GetMapping
    public ResponseEntity<List<DayDto>> findAll() {
        return ResponseEntity.ok(dayRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<Day> addDay(@Valid @RequestBody DayDto dayDto) {
        var day = mapper.DtoToDay(dayDto);
        return ResponseEntity.ok(dayRepository.save(day));
    }

    @GetMapping("/{dayId}")
    public ResponseEntity<Day> getDayWithEvents(@PathVariable Long dayId) {
        var day = dayRepository.findById(dayId).orElseThrow(() -> new DayNotFoundException(dayId));
        return ResponseEntity.ok(day);
    }

    @PatchMapping()
    public ResponseEntity<DayDto> changeDay(@Valid @RequestBody DayDto dayDto) {
        var old = dayRepository.findById(dayDto.getId()).orElseThrow(() -> new DayNotFoundException(dayDto.getId()));
        var day = mapper.DtoToDay(dayDto);
        old.setDayActuality(day.getDayActuality());
        old.setName(day.getName());
        old.setDate(day.getDate());
        old.setDayActuality(day.getDayActuality());
        dayRepository.save(old);
        return ResponseEntity.ok(dayDto);
    }

    @DeleteMapping("/{dayId}")
    public ResponseEntity<String> daleteDay(@PathVariable Long dayId) {
        dayRepository.deleteById(dayId);
        return ResponseEntity.ok("deleted!");
    }
*/
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
