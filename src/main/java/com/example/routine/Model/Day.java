package com.example.routine.Model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.sql.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name = "days")
@Getter
@Setter
@EqualsAndHashCode
public class Day {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "day_date")
    private LocalDate date;

    @Column(name = "day_name")
    private String name;
    @OneToMany(mappedBy = "dayId", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Event> events = new ArrayList<>();
    @Column(name = "day_actuality")
    private DayActuality dayActuality;

    public DayActuality getDayActuality() {
        return dayActuality;
    }

    public void setDayActuality(DayActuality dayActuality) {
        this.dayActuality = dayActuality;
    }


    public void addEvent(Event event) {
        events.add(event);
        event.setDayId(id);
    }

    public void removeEvent(Event event) {
        events.remove(event);
        event.setDayId(null);
    }

}
