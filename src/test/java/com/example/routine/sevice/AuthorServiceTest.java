package com.example.routine.sevice;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;

import com.example.routine.Service.AuthorServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.routine.Model.Event;
import com.example.routine.Repository.EventRepository;

@ExtendWith(MockitoExtension.class)

public class AuthorServiceTest {
	@Mock
	private EventRepository eventRepository;
	private AutoCloseable autoCloseable;
	private AuthorServiceImpl underTest;
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		underTest = new AuthorServiceImpl(eventRepository);
		
	}
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	@Test
	public void test(){
		//given
		var event = new Event();
		event.setStartTime(LocalDateTime.now());
		event.setEndTime(LocalDateTime.now());
		event.setDescription("descr");
		var resEvent = new Event();
		resEvent.setId(1L);
		resEvent.setStartTime(event.getStartTime());
		resEvent.setEndTime(event.getEndTime());
		resEvent.setDescription("descr");
		//when
		when(eventRepository.findAll()).thenReturn(List.of(resEvent));
		underTest.checkIfEventUniq(event);
		//then
		assertEquals(event, resEvent);
	}

	
}
