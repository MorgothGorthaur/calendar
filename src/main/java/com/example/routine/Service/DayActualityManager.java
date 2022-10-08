package com.example.routine.Service;

import com.example.routine.Model.DayActuality;
import com.example.routine.Repository.DayRepository;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@AllArgsConstructor
@Component
public class DayActualityManager {
    private DayRepository dayRepository;
    /*
     * every day sets day`s actuality status, and remove passed days
     */
    @Scheduled(cron = "${dayActualityManagerCronExpression}")
    private void updateDayActuality() {
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
}
