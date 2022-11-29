package com.example.calendar.DTO;

import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter @Setter
public class ParticipantFullDto extends ParticipantDto {
    private String password;
    private String email;

    @Override
    public Participant toParticipant() {
        var participant = new Participant();
        participant.setId(getId());
        participant.setFirstName(getFirstName());
        participant.setLastName(getLastName());
        participant.setStatus(ParticipantStatus.ACTIVE);
        participant.setPassword(password);
        participant.setEmail(email);
        participant.setRole(UserRole.ROLE_USER);
        return participant;
    }

}
