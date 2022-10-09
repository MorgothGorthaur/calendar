package com.example.routine.DTO;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter @Setter
public class ParticipantFullDto extends ParticipantDto {
    private List<EventDto> events;
}
