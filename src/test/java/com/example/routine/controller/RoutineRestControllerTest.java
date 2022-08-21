package com.example.routine.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.routine.Controller.RoutineRestController;
import com.example.routine.DTO.DayDto;
import com.example.routine.DTO.Mapper;
import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Service.DayService;
import com.example.routine.Service.EventService;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest(RoutineRestController.class)
public class RoutineRestControllerTest {
	@Autowired
	ObjectMapper objectMapper;
	
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private DayService dayService;
	@MockBean
	private EventService eventService;
	@MockBean
	private Mapper mapper;
	@MockBean
	private LocalValidatorFactoryBean validator;
	
	private Date today = new Date(Calendar.getInstance().getTime().getTime());
	private Date tomorow = new Date(Calendar.getInstance().getTime().getTime()+24*60*60*1000);
	private Time nextHour = new Time(Calendar.getInstance().getTime().getTime() + 60*60);
	private Time nextTwoHour = new Time(Calendar.getInstance().getTime().getTime() + 2*60*60);
	
	@Test
	public void findAllTest() throws Exception {
		Day day = new Day();
		day.setId((long) 1);
		day.setDate(today);
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		DayDto dayDto = new DayDto();
		dayDto.setDate(today);
		dayDto.setDayActuality(DayActuality.TODAY);
		dayDto.setName("day");
		dayDto.setId((long) 1);
		Mockito.when(dayService.findAll()).thenReturn(Arrays.asList(day));
		Mockito.when(mapper.toDto(day)).thenReturn(dayDto);
		this.mockMvc.perform(get("/routine"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
				.andExpect(jsonPath("$[0].name", equalTo("day")))
				.andExpect(jsonPath("$[0].dayActuality", equalTo("TODAY")))
				.andExpect(jsonPath("$[0].date", equalTo(today.toString())));
        
                		
	}
	@Test
	public void addDayTest() throws Exception {
		Day day = new Day();
		day.setId((long) 1);
		day.setDate(tomorow);
		day.setDayActuality(DayActuality.FUTURE);
		day.setName("day");
		DayDto dayDto = new DayDto();
		dayDto.setDate(tomorow);
		dayDto.setDayActuality(DayActuality.FUTURE);
		dayDto.setName("day");
		dayDto.setId((long) 1);
		Mockito.when(dayService.save(day)).thenReturn(day);
		Mockito.when(mapper.DtoToDay(ArgumentMatchers.any())).thenReturn(day);
		this.mockMvc.perform(post("/routine")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(dayDto)))
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("name", equalTo("day")))
				.andExpect(jsonPath("dayActuality", equalTo("FUTURE")))
				.andExpect(jsonPath("date", equalTo(tomorow.toString())));
	}
	@Test
	public void getDayById() throws Exception {
		Event event = new Event();
		event.setDate(nextHour);
		event.setDescription("description");
		event.setDayId((long)1);
		Day day = new Day();
		day.setId((long) 1);
		day.setDate(tomorow);
		day.setDayActuality(DayActuality.FUTURE);
		day.setName("day");
		day.addEvent(event);
		Mockito.when(dayService.findById((long) 1)).thenReturn(day);
		this.mockMvc.perform(get("/routine/" + 1))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("id", equalTo(1)))
		.andExpect(jsonPath("name", equalTo("day")))
		.andExpect(jsonPath("dayActuality", equalTo("FUTURE")))
		.andExpect(jsonPath("date", equalTo(tomorow.toString())))
		.andExpect(jsonPath("events", hasSize(1)))
		.andExpect(jsonPath("events[0].description", equalTo("description")))
		.andExpect(jsonPath("events[0].time", equalTo(nextHour.toString())));
	}
	
	@Test
	/*
	 * https://stackoverflow.com/questions/61504067/model-mapper-mock-returns-null-object-in-spring-boot-unit-test
	 */
	public void changeDay() throws Exception {
		Day day = new Day();
		day.setId((long) 1);
		day.setDate(today);
		day.setDayActuality(DayActuality.TODAY);
		day.setName("changed day");
		DayDto dayDto = new DayDto();
		dayDto.setId(day.getId());
		dayDto.setDate(day.getDate());
		dayDto.setDayActuality(DayActuality.TODAY);
		dayDto.setName(day.getName());
		Mockito.when(mapper.DtoToDay(ArgumentMatchers.any())).thenReturn(day);
		Mockito.when(mapper.toDto(ArgumentMatchers.any())).thenReturn(dayDto);
		doNothing().when(dayService).updateDay(day);
		assertEquals(day, mapper.DtoToDay(dayDto));
		this.mockMvc.perform(patch("/routine")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(dayDto)))
				.andExpect(jsonPath("name", equalTo("changed day")))
				.andExpect(jsonPath("dayActuality", equalTo("TODAY")))
				.andExpect(jsonPath("date", equalTo(today.toString())));
	}
	@Test
	public void deleteDayById() throws Exception {
		doNothing().when(dayService).daleteById((long) 1);
		MvcResult res = this.mockMvc.perform(delete("/routine/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
				.andReturn();
			assertEquals(res.getResponse().getContentAsString(),"deleted!");
	}
	
	@Test
	public void addEventTest() throws Exception {
		Event event = new Event();
		event.setId((long)1);
		event.setDate(nextTwoHour);
		event.setDescription("description");
		event.setDayId((long) 1);
		Mockito.when(dayService.addEvent(ArgumentMatchers.any())).thenReturn(event);
		this.mockMvc.perform(post("/routine/events")
				
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(event)))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("description", equalTo("description")))
				.andExpect(jsonPath("time", equalTo(nextTwoHour.toString())));
	}
	@Test
	public void deleteEventTest() throws Exception {
		doNothing().when(eventService).deleteById((long)1);
		MvcResult res = this.mockMvc.perform(delete("/routine/events/1"))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
				.andReturn();
			assertEquals(res.getResponse().getContentAsString(),"deleted!");
	}
	
	@Test
	public void changeEvent() throws Exception {
		Event event = new Event();
		event.setDate(nextTwoHour);
		event.setDescription("new description");
		event.setId((long)1);
		event.setDayId((long)1);
		doNothing().when(eventService).updateEvent(event);
		this.mockMvc.perform(patch("/routine/events")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(event)))
				.andExpect(jsonPath("description", equalTo("new description")))
				.andExpect(jsonPath("time", equalTo(nextTwoHour.toString())));
	}
	
}
