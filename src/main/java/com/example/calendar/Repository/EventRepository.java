package com.example.calendar.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.calendar.Model.Event;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {

    @Query(value = "select * from events join participants_events pe on events.id = pe.events_id and events_id = ?1 join participants p on p.id = pe.participants_id and p.email = ?2", nativeQuery = true)
    List<Event> checkIfParticipantContainsEvent(Long eventId, String email);
}
