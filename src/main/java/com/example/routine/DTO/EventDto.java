package com.example.routine.DTO;

import com.example.routine.Model.Event;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Getter @Setter
public class EventDto {
    private Long id;
    @NotNull(message = "date and time must be setted!")
    private LocalDateTime startTime;
    @NotNull(message = "date and time must be setted!")
    private LocalDateTime endTime;
    @NotNull(message = "description must be no null")
    @Size(min = 2, max = 30, message = "description must have size between 2 and 30 literals")
    private String description;
    public Event toEvent(){
        var event = new Event();
        event.setDescription(getDescription());
        event.setStartTime(getStartTime());
        event.setEndTime(getEndTime());
        return event;
    }
}
