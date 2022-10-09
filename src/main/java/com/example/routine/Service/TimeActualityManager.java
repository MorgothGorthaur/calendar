package com.example.routine.Service;

import com.example.routine.Repository.DayRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@Component
public class TimeActualityManager {
    private DayRepository dayRepository;
    @Scheduled(cron="${timeActualityManagerCronExpression}")
    private void updateTimeActuality(){
        try {
//            var day = dayRepository.findDayByDate(LocalDate.now()).orElse(null);
//            if(day != null && day.getEvents() != null){
//                for(var event : day.getEvents()){
//                    if( event.getTime().isBefore(LocalTime.now())){
//                        day.removeEvent(event);
//                        dayRepository.save(day);
//                    }
//                }
//            }
        } catch ( Exception ex) {
            throw ex;
        }
    }
}
