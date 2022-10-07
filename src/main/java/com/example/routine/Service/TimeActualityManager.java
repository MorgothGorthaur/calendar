package com.example.routine.Service;

import com.example.routine.Repository.DayRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
public class TimeActualityManager {
    private DayRepository dayRepository;
    @Scheduled(cron="${timeActualityManagerCronExpression}")
    public void updateTimeActuality(){
        System.err.println("d");
        try {
            dayRepository.findDayByDate(LocalDate.now()).ifPresent(day -> day.getEvents().removeIf(event -> event.getTime().isBefore(LocalTime.now())));
        } catch ( Exception ex) {
            throw ex;
        }
    }
}
