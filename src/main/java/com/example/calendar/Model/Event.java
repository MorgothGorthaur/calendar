package com.example.calendar.Model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "events")
@Getter
@Setter
@ToString

public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "start_time")
    private LocalDateTime startTime;

    @Column(name = "end_time")
    private LocalDateTime endTime;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "events", fetch = FetchType.LAZY)
    private List<Participant> participants;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(startTime, event.startTime) && Objects.equals(endTime, event.endTime) && Objects.equals(description, event.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(startTime, endTime, description);
    }
}
