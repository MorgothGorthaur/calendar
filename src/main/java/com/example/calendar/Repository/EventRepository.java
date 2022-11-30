package com.example.calendar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.calendar.Model.Event;
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

    @Query(value = "select * from events " +
            "join participants_events pe " +
            "on events.id = pe.events_id " +
            "and events.start_Time = ?1 and events.end_time = ?2 and events.description = ?3 " +
            "join participants p on p.id = pe.participants_id and p.email = ?4", nativeQuery = true)
    Optional<Event> checkIfParticipantContainsEventWithSameTimeAndDescription(LocalDateTime startTime, LocalDateTime endTime, String description, String email);
}
