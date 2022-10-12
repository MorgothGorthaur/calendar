package com.example.routine.sevice;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Array;
import java.sql.Time;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.example.routine.Service.EventServiceImpl;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.routine.Model.Event;
import com.example.routine.Repository.EventRepository;
import com.example.routine.exception.EventNotFoundException;

@ExtendWith(MockitoExtension.class)

public class EventServiceTest {
	@Mock
	private EventRepository eventRepository;
	private AutoCloseable autoCloseable;
	private EventServiceImpl underTest;
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		underTest = new EventServiceImpl(eventRepository);
		
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
