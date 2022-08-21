package com.example.routine.sevice;


import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.sql.Time;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;


import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Repository.DayRepository;
import com.example.routine.Service.DayServiceImpl;
import com.example.routine.exception.DayNotFoundException;
import com.example.routine.exception.EventNotFoundException;




@ExtendWith(MockitoExtension.class)
public class DayServiceTest {
	@Mock private DayRepository dayRepository;
	private AutoCloseable autoCloseable;
	private DayServiceImpl underTest;
	private Date today = new Date(Calendar.getInstance().getTime().getTime());
	private Date tomorow = new Date(Calendar.getInstance().getTime().getTime()+24*60*60*1000);
	private Time nextHour = new Time(Calendar.getInstance().getTime().getTime() + 60*60);
	
	@BeforeEach
	void setUp() {
		autoCloseable = MockitoAnnotations.openMocks(this);
		underTest = new DayServiceImpl(dayRepository);
		
	}
	@AfterEach
	void tearDown() throws Exception {
		autoCloseable.close();
	}
	@Test
	public void findAllAuthorsTest() {
		//when
		underTest.findAll();
		//then
		verify(dayRepository).findAll();
	}
	
	@Test
	public void findByIdTest() {
		//given
		Day day = new Day();
		day.setDate(today);
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		when(dayRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(day));
		//when
		Day findedDay = underTest.findById((long) 1);
		//then
		assertEquals(findedDay, day);
	}
	@Test
	public void deleteByIdTest() {
		//given
		Long id = (long) 1;
		
		
		
		doNothing().when(dayRepository).deleteById(id);
		//when
		underTest.deleteById(id);
		verify(dayRepository).deleteById(id);
	}
	@Test
	public void deleteByIdWithExceptionTest() {
		//given
		Long id = (long) 1;
		doThrow(new DayNotFoundException(id)).when(dayRepository).deleteById(id);
		assertThatThrownBy(() -> underTest.deleteById(id)).isInstanceOf(DayNotFoundException.class).hasMessageContaining("Day is not found, id=" + id);
	}
	@Test
	public void updateDayTest() {
		Long id = (long) 1;
		Day day = new Day();
		day.setDate(today);
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		day.setId(id);
		
		when(dayRepository.findById(id)).thenReturn(Optional.of(day));
		underTest.updateDay(day);
		verify(dayRepository).save(day);
	}
	@Test
	public void checkActuality() {
		DayActuality dayActuality = underTest.checkActuality(today);
		assertEquals(dayActuality, DayActuality.TODAY);
	}
	@Test
	public void checkActuality_2() {
		DayActuality dayActuality = underTest.checkActuality(tomorow);
		assertEquals(dayActuality, DayActuality.FUTURE);
	}
	
	@Test
	public void saveTest() {
		Long id = (long) 1;
		Day day = new Day();
		day.setDate(today);
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		day.setId(id);
		
		when(dayRepository.findAll()).thenReturn(Arrays.asList(day));
		underTest.save(day);
		verify(dayRepository).save(day);
	}
	@Test
	public void addEventTest() {
		Long id = (long) 1;
		Day day = new Day();
		day.setDate(today);
		day.setDayActuality(DayActuality.TODAY);
		day.setName("day");
		day.setId(id);
		Event event = new Event();
		event.setDate(nextHour);
		event.setDayId(id);
		event.setDescription("description");
		day.addEvent(event);
		when(dayRepository.findById(id)).thenReturn(Optional.of(day));
		underTest.addEvent(event);
	}
}
