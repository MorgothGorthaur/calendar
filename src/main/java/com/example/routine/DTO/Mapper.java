package com.example.routine.DTO;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.routine.Model.Day;
import com.example.routine.Service.DayService;




@Component
public class Mapper {
	@Autowired
	DayService dayService;
	public DayDto toDto(Day day) {
		DayDto dto = new DayDto();
		dto.setDate(day.getDate());
		dto.setName(day.getName());
		dto.setId(day.getId());
		dto.setDayActuality(day.getDayActuality());
		return dto;
		
	}
	
	public Day DtoToDay(DayDto dto) {
		Day day = new Day();
		day.setDate(dto.getDate());
		day.setName(dto.getName());
		day.setDayActuality(dayService.checkActuality(day.getDate()));
		return day;
	}

}
