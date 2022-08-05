package com.example.routine.Service;

import java.sql.Date;
import java.sql.Time;
import java.util.Calendar;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.example.routine.Model.Day;
import com.example.routine.Model.DayActuality;
import com.example.routine.Model.Event;
import com.example.routine.Repository.DayRepository;
import com.example.routine.exception.DayNotFoundException;

@Service
public class DayServiceImpl implements DayService {
	@Autowired
	DayRepository dayRepository;

	@Override
	public List<Day> findAll() {
		return dayRepository.findAll();
	}

	@Override
	public Day findById(Long id) {
		return dayRepository.findById(id).orElseThrow(() -> new DayNotFoundException(id));
	}

	@Override
	public Long save(Day day) {
		dayRepository.save(day);
		Long id = new Long(0);
		for (Day d : dayRepository.findAll()) {
			if (d.getId() > id) {
				id = d.getId();
			}
		}
		return id;
	}

	@Override
	public void daleteById(Long id) {
	
		dayRepository.delete(findById(id));
		
	}

	@Override
	public void updateDay( Day day) {
		Day updatedDay = findById(day.getId());
		updatedDay.setDate(day.getDate());
		updatedDay.setName(day.getName());
		dayRepository.save(updatedDay);
		
	}

	@Override
	public void addEvent(Event event) {
		Day day = findById(event.getDayId());
		day.addEvent(event);
		save(day);
		
	}

	@Override
	public DayActuality checkActuality(Date date) {
		Date currentDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
		if(currentDate.toLocalDate().compareTo(date.toLocalDate()) == 0) {
			return DayActuality.TODAY;
		} else {
			return DayActuality.FUTURE;
		}
	}
	/*
	 * every day sets day`s actuality status, and remove passed days 
	 */
	@Scheduled(cron = "0 0 0 * * *")
	public void updateDayActuality() {
		try {
			Date currentDate = new Date(Calendar.getInstance().getTime().getTime());
			List <Day> days = dayRepository.findAll();
			for(Day day : days) {
				if(currentDate.toLocalDate().compareTo(day.getDate().toLocalDate()) == 1) {
					dayRepository.deleteById(day.getId());
				} else if (currentDate.toLocalDate().compareTo(day.getDate().toLocalDate()) == -1) {
					day.setDayActuality(DayActuality.FUTURE);
					dayRepository.save(day);
				} else {
					day.setDayActuality(DayActuality.TODAY);
					dayRepository.save(day);
				}
				
			}
		} catch ( Exception ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	/*
	 * every hour checks if day has today`s date, and removes passed events
	 */
	@Transactional
	//@Scheduled(cron="0/5 * * * * *")
	@Scheduled(cron="0 0 * * * *")
	public void updateTimeActuality() {
		try {
			Time currentTime = new Time(Calendar.getInstance().getTime().getTime());		
			List <Day> days = dayRepository.findAll();
			for(Day day : days) {
				if (day.getDayActuality() == DayActuality.TODAY) {
					System.out.println(day.getId());
					for (Event event : day.getEvents()) {
						if (currentTime.toLocalTime().compareTo(event.getTime().toLocalTime()) >= 1) {
							System.out.println(event.getId());
							day.removeEvent(event);
							System.out.println(currentTime.toLocalTime());
							
							
						}
						
					}
				}
				
			}
		} catch ( Exception ex) {
			System.out.println(ex.getMessage());
		}
	}
	
}
