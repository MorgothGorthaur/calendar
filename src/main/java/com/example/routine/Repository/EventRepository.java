package com.example.routine.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.routine.Model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
