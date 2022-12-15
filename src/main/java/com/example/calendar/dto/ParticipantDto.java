package com.example.calendar.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    @NotNull(message = "first name mst be setted!")
    @Size(min = 2, max = 10)
    private String firstName;

    @NotNull(message = "last name mst be setted!")
    @Size(min = 2, max = 10)
    private String lastName;

}
