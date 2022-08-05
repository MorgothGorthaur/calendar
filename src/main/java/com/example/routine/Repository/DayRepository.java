package com.example.routine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.routine.Model.Day;

public interface DayRepository extends JpaRepository<Day,Long> {

}
