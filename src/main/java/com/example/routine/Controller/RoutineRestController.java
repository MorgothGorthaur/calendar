package com.example.routine.Controller;


import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;


import com.example.routine.Repository.DayRepository;
import com.example.routine.Repository.EventRepository;
import com.example.routine.exception.DayNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.routine.DTO.DayDto;
import com.example.routine.DTO.Mapper;
import com.example.routine.Model.Day;
import com.example.routine.Model.Event;


@RestController
@RequestMapping("/routine")
@CrossOrigin(origins = "*")
public class RoutineRestController {
    @Autowired
    private DayRepository dayRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private Mapper mapper;

    @GetMapping
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
        Day day = mapper.DtoToDay(dayDto);
        day.setId(dayDto.getId());
        dayRepository.save(day);
        return ResponseEntity.ok(mapper.toDto(day));
    }

    @DeleteMapping("/{dayId}")
    public ResponseEntity<String> daleteDay(@PathVariable Long dayId) {
        dayRepository.deleteById(dayId);
        return ResponseEntity.ok("deleted!");
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
        System.out.println(event);
        var day = eventRepository.save(event);
        System.out.println(event);
        return ResponseEntity.ok(day);
    }
}
