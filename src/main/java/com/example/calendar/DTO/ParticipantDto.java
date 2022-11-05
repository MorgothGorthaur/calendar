package com.example.calendar.DTO;

import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter @Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ParticipantDto {
    private Long id;
    @NotNull(message = "first name mst be setted!")
    @Size(min = 2, max = 10)
    private String firstName;

    @NotNull(message = "last name mst be setted!")
    @Size(min = 2, max = 10)
    private String lastName;
    public Participant toParticipant(){
        var participant = new Participant();
        participant.setId(id);
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setStatus(ParticipantStatus.ACTIVE);
        return participant;
    }
}
