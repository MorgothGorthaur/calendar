package com.example.routine.Service;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Repository.DayRepository;
import com.example.routine.exception.DayNotFoundException;

@Service
public class DayServiceImpl{
	@Autowired
	private DayRepository dayRepository;


	/*
	 * every day sets day`s actuality status, and remove passed days 
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void updateDayActuality() {
		try {

			var actualDate = LocalDate.now();
			for (var day : dayRepository.findAll()){
				if(day.getDate().isBefore(actualDate)){
					dayRepository.delete(day);
				} else if (day.getDate().isEqual(actualDate)) {
					day.setDayActuality(DayActuality.TODAY);
					dayRepository.save(day);
				}
			}
		} catch ( Exception ex) {
			throw  ex;
		}
		
	}
	/*
	 * every hour checks if day has today`s date, and removes passed events
	 */
	//@Transactional
	//@Scheduled(cron="0/30 * * * * *")
	@Scheduled(cron="0 0 * * * *")
	public void updateTimeActuality() {
		try {
			dayRepository.findDayByDate(LocalDate.now()).ifPresent(day -> day.getEvents().removeIf(event -> event.getTime().isBefore(LocalTime.now())));
		} catch ( Exception ex) {
			throw ex;
		}
	}


	
}
