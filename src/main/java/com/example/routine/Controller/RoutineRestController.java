package com.example.routine.Controller;



import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;


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
import com.example.routine.Service.DayService;
import com.example.routine.Service.EventService;
import com.example.routine.exception.DayNotFoundException;
import com.example.routine.exception.EventNotFoundException;

@RestController
@RequestMapping("/routine")
@CrossOrigin(origins="*")
public class RoutineRestController {
	
	@Autowired
	DayService dayService;
	@Autowired
	EventService eventService;
	@Autowired
	Mapper mapper;
	
	@GetMapping
	public ResponseEntity<List<DayDto>> findAll(){
		return ResponseEntity.ok(dayService.findAll().stream().map(mapper::toDto).collect(Collectors.toList()));
	}
	@PostMapping
	public ResponseEntity<Long> addDay(@Valid @RequestBody  DayDto dayDto){
		Day day = mapper.DtoToDay(dayDto);
		
		return ResponseEntity.ok(dayService.save(day));
	}
	
	@GetMapping("/{dayId}")
	public ResponseEntity<Day> getDayWithEvents(@PathVariable Long dayId) throws DayNotFoundException{
		Day day = dayService.findById(dayId);
		return ResponseEntity.ok(day);
	}
	
	@PatchMapping()
	public ResponseEntity<Long>  changeDay(@Valid @RequestBody DayDto dayDto) throws DayNotFoundException{
		Day day = mapper.DtoToDay(dayDto);
		day.setId(dayDto.getId());
		dayService.updateDay(day);
		return ResponseEntity.ok(day.getId());				
	}
	
	@DeleteMapping("/{dayId}")
	public ResponseEntity<String> daleteDay(@PathVariable Long dayId) throws DayNotFoundException{
			dayService.daleteById(dayId);
			return ResponseEntity.ok("deleted!");	
	}
	
	@PostMapping("/events")
	public ResponseEntity<String> addEvent( @Valid @RequestBody Event event) throws DayNotFoundException{
		dayService.addEvent(event);
		return ResponseEntity.ok("added!");
	}
	
	@DeleteMapping("/events/{eventId}")
	public ResponseEntity<String> deleteEvent(@PathVariable Long eventId) throws EventNotFoundException{
		eventService.deleteById(eventId);
		return ResponseEntity.ok("deleted!");
	}
	
	@PatchMapping("/events")
	public ResponseEntity<String> changeEvent(@Valid @RequestBody Event event) throws EventNotFoundException {
		System.out.println(event.getId());
		eventService.updateEvent(event);
		return ResponseEntity.ok("changed!");		
	}
	
}
