package com.example.routine.DTO;

import com.example.routine.Model.Event;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter @Setter
public class EventFullDto extends EventDto{
    List<ParticipantDto> participants;
}
