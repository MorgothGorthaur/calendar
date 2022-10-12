package com.example.routine.Service;


import com.example.routine.Model.ParticipantStatus;
import com.example.routine.Repository.EventRepository;
import com.example.routine.Repository.ParticipantRepository;
import com.example.routine.exception.EventNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;


@AllArgsConstructor
@Component
public class TimeActualityManager {

    private EventRepository eventRepository;
    private ParticipantRepository participantRepository;

    @Scheduled(cron = "${timeActualityManagerCronExpression}")
    private void updateTimeActuality() {
        System.out.println("manage work!");
        try {
            var time = LocalDateTime.now();
            var participants = participantRepository.findParticipantByStatus(ParticipantStatus.ACTIVE);
            for (var participant : participants) {
                System.out.println(participant.getEvents().size());
                for (var event : participant.getEvents()) {
                    System.out.println(event.getDescription());
                    if (event.getEndTime().isBefore(time)) {
                        System.out.println(event.getEndTime());
                        var ev = eventRepository.findById(event.getId()).orElseThrow(()-> new EventNotFoundException(event.getId()));
                        participant.removeEvent(ev);
                        participantRepository.save(participant);
                    }
                }
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
}
