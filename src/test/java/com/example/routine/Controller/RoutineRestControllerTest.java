package com.example.routine.Controller;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

import java.util.Calendar;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;


import com.example.routine.DTO.DayDto;
import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Repository.DayRepository;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Service.DayService;
import com.example.routine.Service.EventService;


import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoutineRestControllerTest {
	
	
	/*@Autowired 
	private TestRestTemplate restTemplate;*/
	/*@Autowired
	private DayService dayService;
	@Autowired
	private DayRepository dayRepository;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	EventRepository eventRepository;
	@Autowired 
	RoutineRestController routineController;
	
	@Test
	public void findAllTest() {
		dayRepository.deleteAll();
		eventRepository.deleteAll();
		ResponseEntity <List <DayDto>> response = routineController.findAll();
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().size(), 0);
	}
	
	
	@Test
	public void addDayTest() {
		DayDto day = new DayDto();
		day.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		ResponseEntity <Long> response = routineController.addDay(day);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(dayService.findById(response.getBody()).getName(), "day");
		
	}
	
	@Test
	public void getDayWithEvents() {
		Day day = new Day();
		day.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		Long id = dayService.save(day);
		ResponseEntity <Day> response = routineController.getDayWithEvents(id);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getName(), day.getName());
	}
	@Test
	public void changeDayTest() {
		Day day = new Day();
		day.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		
		Long id = dayService.save(day);
		DayDto dayDto = new DayDto();
		dayDto.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		dayDto.setId(id);
		dayDto.setDayActuality(DayActuality.TODAY);
		dayDto.setName("changed day");
		ResponseEntity <Long> response = routineController.changeDay(dayDto);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(dayService.findById(response.getBody()).getName(), dayDto.getName());
	}
	
	@Test
	public void deleteDayTest() {
		Day day = new Day();
		day.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		
		Long id = dayService.save(day);
		ResponseEntity <String> response = routineController.daleteDay(id);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), "deleted!");
	}
	
	
	@Test
	public void addEventTest() {
		Day day = new Day();
		day.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		
		Long id = dayService.save(day);
		Event event = new Event();
		event.setDate(Time.valueOf(LocalTime.now()));
		event.setDayId(id);
		event.setDescription("do somethin");
		
		ResponseEntity <String> response = routineController.addEvent(event);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), "added!");
		assertEquals(dayService.findById(id).getEvents().get(0).getDescription(), event.getDescription());
	}
	
	@Test
	public void deleteEventTest() {
		Day day = new Day();
		day.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		
		Long id = dayService.save(day);
		Event event = new Event();
		event.setDate(Time.valueOf(LocalTime.now()));
		event.setDayId(id);
		event.setDescription("do somethin");
		dayService.addEvent(event);
		Long eventId = dayService.findById(id).getEvents().get(0).getId();
		ResponseEntity <String> response = routineController.deleteEvent(eventId);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), "deleted!");
		
		
	}
	@Test
	public void changeEventTest() {
		Day day = new Day();
		day.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		
		Long id = dayService.save(day);
		Event event = new Event();
		event.setDate(Time.valueOf(LocalTime.now()));
		event.setDayId(id);
		event.setDescription("do somethin");
		dayService.addEvent(event);
		Long eventId = dayService.findById(id).getEvents().get(0).getId();
		
		Event changedEvent = new Event();
		changedEvent.setDate(Time.valueOf(LocalTime.now()));
		changedEvent.setDayId(id);
		changedEvent.setDescription("do somethin else");
		changedEvent.setId(eventId);
		ResponseEntity <String> response = routineController.changeEvent(changedEvent);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody(), "changed!");
		assertEquals(eventService.findById(eventId).getDescription(), changedEvent.getDescription());
	}*/
	/*@Test
	public void findAllTest () {
		dayRepository.deleteAll();
		List <Day> days = new ArrayList <> ();
		Day firstDay = new Day();
		firstDay.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		firstDay.setDayActuality(DayActuality.TODAY);
		firstDay.setName("firstName");
		
		dayRepository.save(firstDay);
		Day secondDay = new Day();
		secondDay.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		secondDay.setDayActuality(DayActuality.TODAY);
		secondDay.setName("secondName");
		dayRepository.save(secondDay);
		ResponseEntity<List<DayDto>> response = restTemplate.exchange("/routine", HttpMethod.GET, null,
				    new ParameterizedTypeReference<List<DayDto>>() {
				    });
				  List<DayDto> persons = response.getBody();
				  assertThat(persons).hasSize(2);
				  //assertThat(persons.get(1).getName(), is("Jane"));
		
	}
	@Test
	public void addDayTest() throws JSONException {
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject firstDayJsonObject = new JSONObject();
	    firstDayJsonObject.put("date",new Date(Calendar.getInstance().getTime().getTime()) );
	    firstDayJsonObject.put("name", "day");
	    HttpEntity<String> request = new HttpEntity<String>(firstDayJsonObject.toString(), headers);
	    Long dayId = restTemplate.postForObject("/routine", request, Long.class);
	    
	   
	  
	    Day day = dayService.findById(dayId);
	    assertThat(day.getDate().toLocalDate()).isEqualTo(new Date(Calendar.getInstance().getTime().getTime()).toLocalDate() );
	    assertThat(day.getDayActuality()).isEqualTo(DayActuality.TODAY);
	    assertThat(day.getName()).isEqualTo("day");
	    
	}
	
	@Test
	public void getDayEventsTest() {
		
		Day firstDay = new Day();
		firstDay.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		firstDay.setDayActuality(DayActuality.TODAY);
		firstDay.setName("d");
		Long id = dayService.save(firstDay);
		Event firstEvent = new Event();
		firstEvent.setDate(Time.valueOf(LocalTime.now()));
		firstEvent.setDayId(id);
		firstEvent.setDescription("some descr");
		//dayService.addEvent(firstEvent);
		eventRepository.save(firstEvent);
		
		
		ResponseEntity<Day> response = restTemplate.getForEntity("/routine/" + id, Day.class);
		assertEquals(response.getStatusCode(), HttpStatus.OK);
		assertEquals(response.getBody().getName(), "d");
	}
	
	@Test
	public void changeDayTest() throws JSONException {
		Day firstDay = new Day();
		firstDay.setDate(new Date(Calendar.getInstance().getTime().getTime()));
		firstDay.setDayActuality(DayActuality.TODAY);
		firstDay.setName("d");
		Long id = dayService.save(firstDay);
		
		
		HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.APPLICATION_JSON);
	    JSONObject firstDayJsonObject = new JSONObject();
	    firstDayJsonObject.put("date",new Date(Calendar.getInstance().getTime().getTime()) );
	    firstDayJsonObject.put("name", "day");
	    firstDayJsonObject.put("id", id);
	    HttpEntity<String> request = new HttpEntity<String>(firstDayJsonObject.toString(), headers);
	   
	    //Long response = restTemplate.patchForObject("/routine/", request, Long.class);
	    //String result = restTemplate.patchForObject("/routine/" + id, "Hello World", String.class);
	    //assertEquals(id, dayId);
	}*/
}
