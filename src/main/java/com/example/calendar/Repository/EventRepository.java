package com.example.calendar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.calendar.Model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

}
