package com.example.routine.DTO;

import com.example.routine.Model.Event;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter @Setter
public class EventDto {
    private Long id;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
    public Event toEvent(){
        var event = new Event();
        event.setDescription(getDescription());
        event.setStartTime(getStartTime());
        event.setEndTime(getEndTime());
        return event;
    }
}
