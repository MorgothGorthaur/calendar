package com.example.calendar.model;

import com.example.calendar.exception.ParticipantAlreadyContainsEvent;
import com.example.calendar.exception.ParticipantDoesntContainsThisEvent;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "participants")
@Getter
@Setter
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Event> events;
    private ParticipantStatus status;

    @Column(name = "user_role")
    private UserRole role;

    private String password;
    private String email;

    public void addEvent(Event event) {
        if (events != null) {
            if (!events.contains(event)) {
                events.add(event);
            } else {
                throw new ParticipantAlreadyContainsEvent();
            }
        } else {
            events = new HashSet<>();
            events.add(event);
        }
    }

    public void removeEvent(Event event) {
        if (events != null && events.contains(event)) {
            events.remove(event);
        } else {
            throw new ParticipantDoesntContainsThisEvent(event.getId());
        }

    }


}
