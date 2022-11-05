package com.example.calendar.sevice;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.Service.AuthorServiceImpl;
import com.example.calendar.exception.ParticipantAlreadyContainsEvent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.calendar.Model.Event;
import com.example.calendar.Repository.EventRepository;

@ExtendWith(MockitoExtension.class)

public class AuthorServiceTest {
	@Mock
	private EventRepository eventRepository;
	@Mock
	private ParticipantRepository participantRepository;
	private AutoCloseable autoCloseable;
	private AuthorServiceImpl underTest;
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		underTest = new AuthorServiceImpl(eventRepository, participantRepository);
		
	}
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	@Test
	public void addEvent_shouldReturnException(){
		var participant = new Participant();
		participant.setId(1L);
		participant.setFirstName("Victor");
		participant.setLastName("Tarasov");
		participant.setStatus(ParticipantStatus.ACTIVE);
		var event = new Event();
		event.setId(1L);
		event.setDescription("descr");
		participant.setEvents(List.of(event));
		var exception = assertThrows(ParticipantAlreadyContainsEvent.class, () -> {
			underTest.addEvent(participant, event);
		});
		assertEquals(exception.getMessage(), "participant already contains event!");
	}

	@Test
	public void addEvent_shouldReturnListOfEvents(){
		var participant = new Participant();
		participant.setId(1L);
		participant.setFirstName("Victor");
		participant.setLastName("Tarasov");
		participant.setStatus(ParticipantStatus.ACTIVE);
		var event = new Event();
		event.setId(1L);
		event.setDescription("descr");
		var participantWithEvent = new Participant();
		participantWithEvent.setId(1L);
		participantWithEvent.setFirstName("Victor");
		participantWithEvent.setLastName("Tarasov");
		participantWithEvent.setStatus(ParticipantStatus.ACTIVE);
		participantWithEvent.addEvent(event);
		event.setParticipants(List.of(participantWithEvent));
		when(participantRepository.save(participant)).thenReturn(participantWithEvent);
		when(eventRepository.findAll()).thenReturn(List.of(event));

		var events = underTest.addEvent(participant,event);
		assertEquals(events, List.of(event));
		assertEquals(events.get(0).getParticipants(), List.of(participantWithEvent));

	}
	
}
