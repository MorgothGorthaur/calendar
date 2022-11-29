package com.example.calendar.DTO;

import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@Getter @Setter
public class ParticipantFullDto extends ParticipantDto {
    @NotNull(message = "password name mst be setted!")
    @Size(min = 2, max = 10)
    private String password;

    @NotNull(message = "email name mst be setted!")
    @Email
    private String email;
    
    public Participant toParticipant() {
        var participant = new Participant();
        participant.setFirstName(getFirstName());
        participant.setLastName(getLastName());
        participant.setStatus(ParticipantStatus.ACTIVE);
        participant.setPassword(password);
        participant.setEmail(email);
        participant.setRole(UserRole.ROLE_USER);
        return participant;
    }

}
