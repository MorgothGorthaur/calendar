package com.example.calendar.dto;

import com.example.calendar.model.Participant;
import com.example.calendar.model.ParticipantStatus;
import com.example.calendar.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@AllArgsConstructor
@NoArgsConstructor
@Getter @Setter
public class ParticipantFullDto {
    @NotNull(message = "first name mst be setted!")
    @Size(min = 2, max = 10)
    private String firstName;

    @NotNull(message = "last name mst be setted!")
    @Size(min = 2, max = 10)
    private String lastName;
    @NotNull(message = "password name mst be setted!")
    @Size(min = 2, max = 10)
    private String password;

    @NotNull(message = "email name mst be setted!")
    @Email
    private String email;
    
    public Participant toParticipant() {
        var participant = new Participant();
        participant.setFirstName(firstName);
        participant.setLastName(lastName);
        participant.setStatus(ParticipantStatus.ACTIVE);
        participant.setPassword(password);
        participant.setEmail(email);
        participant.setRole(UserRole.ROLE_USER);
        return participant;
    }

}
