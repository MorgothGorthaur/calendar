package com.example.calendar.model;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import javax.persistence.*;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "events")
@Getter
@Setter
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
        return startTime.equals(event.startTime) && endTime.equals(event.endTime) && description.equals(event.description);
    }

    @Override
    public int hashCode() {
        var hash = 7;
        hash = hash * 31 + (startTime != null ? startTime.hashCode() : 0);
        hash = hash * 31 + (endTime != null ? endTime.hashCode() : 0);
        hash = hash * 31 + (description != null ? description.hashCode() : 0);
        return hash;
    }
}
