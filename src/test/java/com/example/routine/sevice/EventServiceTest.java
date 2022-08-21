package com.example.routine.sevice;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Time;
import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.routine.Model.Event;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Service.EventServiceImpl;
import com.example.routine.exception.EventNotFoundException;

@ExtendWith(MockitoExtension.class)
public class EventServiceTest {
	@Mock
	private EventRepository eventRepository;
	private AutoCloseable autoCloseable;
	private EventServiceImpl underTest;
	private Time nextHour = new Time(Calendar.getInstance().getTime().getTime() + 60*60);
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
	public void deleteByIdTest() {
		Long id = (long) 1;	
		doNothing().when(eventRepository).deleteById(id);
		underTest.deleteById(id);
		verify(eventRepository).deleteById(id);
	}
	@Test
	public void deleteByIdWithExceptionTest() {
		Long id = (long) 1;
		
		doThrow(new EventNotFoundException(id)).when(eventRepository).deleteById(id);
		//when
		//then
		assertThatThrownBy(() -> underTest.deleteById(id)).isInstanceOf(EventNotFoundException.class).hasMessageContaining("Event is not found, id=" + id);
	}
	@Test
	public void findByIdTest() {
		Event event = new Event();
		event.setId((long)1);
		event.setDescription("description");
		event.setDate(nextHour);
		when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));
		Event findedEvent = underTest.findById(event.getId());
		assertEquals(event, findedEvent);
	}
	@Test
	public void updateEventTest() {
		Event event = new Event();
		event.setId((long)1);
		event.setDescription("description");
		event.setDate(nextHour);
		when(eventRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(event));
		underTest.updateEvent(event);
		verify(eventRepository).save(event);
	}
	
}
