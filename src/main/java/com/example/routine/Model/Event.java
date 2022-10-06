package com.example.routine.Model;


import java.sql.Time;
import java.time.LocalTime;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.routine.validation.CheckIfTimeIsActualValidation;
import com.example.routine.validation.CheckIfTimeIsUniqueValidation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "events")
@CheckIfTimeIsUniqueValidation
@CheckIfTimeIsActualValidation
@Getter
@Setter
@EqualsAndHashCode
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "events_time")
    @NotNull(message = "time must be setted!")
    private LocalTime time;
    private Long dayId;
    @Column(name = "description")
    @NotNull(message = "description must be no null")
    @Size(min = 2, max = 30, message = "description must have size between 2 and 30 literals")
    public String description;

}
