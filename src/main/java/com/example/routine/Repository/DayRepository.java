package com.example.routine.Repository;

import com.example.routine.Model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.routine.Model.Day;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface DayRepository extends JpaRepository<Day,Long> {
    Optional<Day> findDayByDate(LocalDate date);

}
