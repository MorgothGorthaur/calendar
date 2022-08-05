package com.example.routine.Controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.example.routine.DTO.DayDto;
import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Repository.DayRepository;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Service.DayService;
import com.example.routine.Service.EventService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class RoutineRestControllerTest {
	
	
	@Autowired 
	private TestRestTemplate restTemplate;
	@Autowired
	private DayService dayService;
	@Autowired
	private DayRepository dayRepository;
	
	@Autowired
	EventService eventService;
	
	@Autowired
	EventRepository eventRepository;
	@Test
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
	
	/*@Test
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
	    Long response = restTemplate.patchForObject("/routine/", request, Long.class);
	    //String result = restTemplate.patchForObject("/routine/" + id, "Hello World", String.class);
	    //assertEquals(id, dayId);
	}*/
}
