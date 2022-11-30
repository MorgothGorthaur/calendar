package com.example.calendar.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
    private List<Event> events;
    private ParticipantStatus status;

    @Column(name = "user_role")
    private  UserRole role;

    private String password;
    private String email;

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
