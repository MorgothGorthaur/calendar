package com.example.routine.DTO;

import com.example.routine.Model.Participant;
import com.example.routine.Model.ParticipantStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ParticipantDto {
    private Long id;
    private String firstName;
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
