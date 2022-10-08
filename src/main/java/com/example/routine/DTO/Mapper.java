package com.example.routine.DTO;


import com.example.routine.Model.DayActuality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.example.routine.Model.Day;

import java.time.LocalDate;


@Component
public class Mapper {

    public DayDto toDto(Day day) {
        var dto = new DayDto();
        dto.setDate(day.getDate());
        dto.setName(day.getName());
        dto.setId(day.getId());
        dto.setDayActuality(day.getDayActuality());
        return dto;

    }

    public Day DtoToDay(DayDto dto) {
        var day = new Day();
        day.setDate(dto.getDate());
        day.setName(dto.getName());
        day.setDayActuality(DayActuality.FUTURE);
        if (day.getDate().isEqual(LocalDate.now())) {
            day.setDayActuality(DayActuality.TODAY);
        }
        return day;
    }

}
