package com.example.routine.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.LinkedList;
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
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Event> events;
    private ParticipantStatus status;

    public void addEvent(Event event){
        var tmp = events;
        events = new LinkedList<>();
        events.addAll(tmp);
        events.add(event);
    }
    public void removeEvent(Event event){
        if(events != null) {
            events.remove(event);
            event.getParticipants().remove(this);
        }
    }

}
