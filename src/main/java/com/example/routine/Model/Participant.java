package com.example.routine.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "participants")
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    @NotNull(message = "first name mst be setted!")
    @Size(min = 2, max = 10)
    private String firstName;
    @Column(name = "last_name")
    @NotNull(message = "last name mst be setted!")
    @Size(min = 2, max = 10)
    private String lastName;
    @ManyToMany
    private List<Event> events;
    private ParticipantStatus status;

    public void addEvent(Event event){
        if(events == null){
            events = new ArrayList<>();
        }
        events.add(event);
    }

}
