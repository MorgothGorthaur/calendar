package com.example.routine.DTO;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter @Setter
public class EventDto {
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String description;
}
