package com.example.calendar.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
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
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> events;
    private ParticipantStatus status;

    public void addEvent(Event event){
        if(events != null) {
            var tmp = events;
            events = new LinkedList<>();
            events.addAll(tmp);
            events.add(event);
        } else {
            events = new LinkedList<>();
            events.add(event);
        }
    }
    public void removeEvent(Event event){
        if(events != null) {
            events.remove(event);
            event.getParticipants().remove(this);
        }
    }

}
