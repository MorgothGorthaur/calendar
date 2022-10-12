package com.example.routine.controller;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Date;

import java.util.Calendar;

import com.example.routine.Repository.ParticipantRepository;
import com.example.routine.Service.EventService;
import com.example.routine.exception.ParticipantNotFoundException;
import org.apache.catalina.mapper.Mapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


@WebMvcTest

public class RestExceptionHandlerTest {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ParticipantRepository participantRepository;
	@MockBean
	private EventService eventService;
	@MockBean
	private Mapper mapper;
	private Date today = new Date(Calendar.getInstance().getTime().getTime());

	@Test
	@Disabled
	public void  handleEntityNotFoundExTest() throws Exception {
		Mockito.when(participantRepository.findById((long)1)).thenThrow(new ParticipantNotFoundException((long)1));
		this.mockMvc.perform(get("/routine/1"))
			.andExpect(status().isNotFound())
			.andExpect(content().contentType(MediaType.APPLICATION_JSON))
			.andExpect(jsonPath("debugMessage", equalTo("Participant is not found, id=1")));
							
	}

}
