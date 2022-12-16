package com.example.calendar.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.calendar.model.Event;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select * from events" +
            " join participants_events pe " +
            "on events.id = pe.events_id and events.id = ?1 " +
            "join participants p " +
            "on p.id = pe.participants_id and p.email = ?2", nativeQuery = true)
    Optional<Event> checkIfParticipantContainsEventWithId(Long eventId, String email);

    @Query(value = "select * from events" +
            " join participants_events pe " +
            "on events.id = pe.events_id " +
            "join participants p" +
            " on p.id = pe.participants_id and p.email = ?1 and p.status = 0", nativeQuery = true)
    List<Event> getEventsByEmail(String email);


}
