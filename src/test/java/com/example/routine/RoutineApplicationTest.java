package com.example.routine;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;
import java.sql.Time;

import java.util.Calendar;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;


import com.example.routine.Controller.RoutineRestController;
import com.example.routine.DTO.DayDto;
import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Repository.DayRepository;
import com.example.routine.Repository.EventRepository;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
public class RoutineApplicationTest {
	@Autowired
	private RoutineRestController restController;
	@Autowired
	private DayRepository dayRepository;
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private ObjectMapper objectMapper;
	@Autowired
	private MockMvc mockMvc;
	@Test
	public void contextLoads() {
		assertThat(restController).isNotNull();
	}
	private Date today = new Date(Calendar.getInstance().getTime().getTime());
	private Date tomorow = new Date(Calendar.getInstance().getTime().getTime()+24*60*60*1000);
	private Time nextHour = new Time(Calendar.getInstance().getTime().getTime() + 60*60);
	private Time nextTwoHour = new Time(Calendar.getInstance().getTime().getTime() + 2*60*60);
	@BeforeEach
	public void addData() {
		Day day = new Day();
		day.setDate(today);
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		Event event = new Event();
		event.setDate( nextHour);
		event.setDescription("description");
		dayRepository.save(day);
		event.setDayId(dayRepository.findAll().get(0).getId());
		eventRepository.save(event);
	}
	@AfterEach
	public void resetDb() {
		dayRepository.deleteAll();
		eventRepository.deleteAll();
	}
	@Test
	public void findAllTest() throws Exception {
		//when
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
		DayDto dayDto = new DayDto();
		dayDto.setDate(tomorow);
		dayDto.setName("day");
		this.mockMvc.perform(post("/routine")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(dayDto)))
				.andExpect(jsonPath("name", equalTo("day")))
				.andExpect(jsonPath("dayActuality", equalTo("FUTURE")))
				.andExpect(jsonPath("date", equalTo(tomorow.toString())));
	}
	@Test
	public void getDayById() throws Exception {
		Long id = dayRepository.findAll().get(0).getId();
		this.mockMvc.perform(get("/routine/" + id))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
		.andExpect(jsonPath("id", equalTo(id.intValue())))
		.andExpect(jsonPath("name", equalTo("day")))
		.andExpect(jsonPath("dayActuality", equalTo("TODAY")))
		.andExpect(jsonPath("date", equalTo(today.toString())))
		.andExpect(jsonPath("events", hasSize(1)))
		.andExpect(jsonPath("events[0].description", equalTo("description")))
		.andExpect(jsonPath("events[0].time", equalTo(nextHour.toString())));
	}
	
	@Test
	public void changeDay() throws Exception {
		Long id = dayRepository.findAll().get(0).getId();
		DayDto dayDto = new DayDto();
		dayDto.setId(id);
		dayDto.setDate(today);
		dayDto.setName("changed day");
		this.mockMvc.perform(patch("/routine")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(dayDto)))
				.andExpect(jsonPath("name", equalTo("changed day")))
				.andExpect(jsonPath("dayActuality", equalTo("TODAY")))
				.andExpect(jsonPath("date", equalTo(today.toString())));
	}
	@Test
	public void deleteDayById() throws Exception {
		Long id = dayRepository.findAll().get(0).getId();
		MvcResult res = this.mockMvc.perform(delete("/routine/" + id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
				.andReturn();
			assertEquals(res.getResponse().getContentAsString(),"deleted!");
	}
	@Test
	public void addEventTest() throws Exception {
		Long dayId = dayRepository.findAll().get(0).getId();
		Event event = new Event();
		event.setDate(nextTwoHour);
		event.setDescription("description");
		event.setDayId(dayId);
		this.mockMvc.perform(post("/routine/events")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(event)))
				.andExpect(jsonPath("description", equalTo("description")))
				.andExpect(jsonPath("time", equalTo(nextTwoHour.toString())));
	}
	
	@Test
	public void deleteEventTest() throws Exception {
		Long id = eventRepository.findAll().get(0).getId();
		MvcResult res = this.mockMvc.perform(delete("/routine/events/" + id))
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
				.andReturn();
			assertEquals(res.getResponse().getContentAsString(),"deleted!");
	}
	@Test
	public void changeEvent() throws Exception {
		Long id = eventRepository.findAll().get(0).getId();
		Long dayId = dayRepository.findAll().get(0).getId();
		Event event = new Event();
		event.setDate(nextTwoHour);
		event.setDescription("new description");
		event.setId(id);
		event.setDayId(dayId);
		this.mockMvc.perform(patch("/routine/events")
	            .contentType(MediaType.APPLICATION_JSON)
	            .content(objectMapper.writeValueAsString(event)))
				.andExpect(jsonPath("description", equalTo("new description")))
				.andExpect(jsonPath("time", equalTo(nextTwoHour.toString())));
	}
	@Test
	public void  handleEntityNotFoundExTest() throws Exception {
		
		this.mockMvc.perform(get("/routine/1"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("debugMessage", equalTo("Day is not found, id=1")));
							
	}
	@Test
	public void handleInvalidArgument() throws Exception {
		DayDto dayDto = new DayDto();
		dayDto.setDate(today);
		dayDto.setName("day");
		
		this.mockMvc.perform(post("/routine")
	            .contentType(MediaType.APPLICATION_JSON)
            	.content(objectMapper.writeValueAsString(dayDto)))
		 		.andExpect(status().isBadRequest())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("message", equalTo("validation error")));
				//.andDo(print());
	}
	@Test
	public void HttpMessageNotReadableExceptionTest() throws Exception {
		this.mockMvc.perform(patch("/routine")
	            .contentType(MediaType.APPLICATION_JSON)
            	.content("ddd"))
		 		.andExpect(status().isBadRequest())
		 		.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("message", equalTo("Malformed JSON Request")));
				//.andDo(print());
	}
	
	@Test
	public void MethodArgumentTypeMismatchExceptionTest() throws Exception {
		
		this.mockMvc.perform(get("/routine/ddd"))
			.andExpect(status().isBadRequest())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("message", equalTo("The parameter 'dayId' of value 'ddd' could not be converted to type 'Long'")));

	}
	
	@Test
	@Disabled
	public void handleNoHandlerFoundExceptionTest() throws Exception {
		this.mockMvc.perform(get("/ddd"))
			.andExpect(status().isNotFound());
	}
}
