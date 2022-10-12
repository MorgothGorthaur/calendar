package com.example.routine.Model;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.example.routine.validation.CheckIfTimeIsActualValidation;
import com.example.routine.validation.CheckIfTimeIsUniqueValidation;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Entity
@Table(name = "events")
@CheckIfTimeIsUniqueValidation
@CheckIfTimeIsActualValidation
@Getter
@Setter
@EqualsAndHashCode
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

    @ManyToMany(mappedBy = "events")
    private List<Participant> participants;

}
