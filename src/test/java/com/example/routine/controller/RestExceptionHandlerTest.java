package com.example.routine.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import java.util.Calendar;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.routine.DTO.DayDto;
import com.example.routine.DTO.Mapper;
import com.example.routine.exception.DayNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
@WebMvcTest
public class RestExceptionHandlerTest {
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
	private Date today = new Date(Calendar.getInstance().getTime().getTime());

	@Test
	public void  handleEntityNotFoundExTest() throws Exception {
		Mockito.when(dayService.findById((long)1)).thenThrow(new DayNotFoundException((long)1));
		this.mockMvc.perform(get("/routine/1"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("debugMessage", equalTo("Day is not found, id=1")));
							
	}
	@Test
	public void handleInvalidArgument() throws Exception {
		DayDto dayDto = new DayDto();
		dayDto.setDate(today);
		dayDto.setName("d");
		
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
	public void handleNoHandlerFoundExceptionTest() throws Exception {
		this.mockMvc.perform(get("/ddd"))
			.andExpect(status().isNotFound());
	}
}
