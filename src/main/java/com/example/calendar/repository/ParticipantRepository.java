package com.example.calendar.repository;

import com.example.calendar.model.Participant;
import com.example.calendar.model.ParticipantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findParticipantByStatus(ParticipantStatus status);
    Optional<Participant> findByEmail(String email);
    Optional<Participant> findByEmailAndStatus(String email, ParticipantStatus status);
    @Query(value = "select p from Participant p where p.email = ?1 and p.id <> ?2")
    List<Participant> findParticipantsWithEqualEmailAndNonEqualId(String email, Long id);

    @Query(value = "select * from events join participants_events pe on events.id = pe.events_id and events.id = ?1 join participants p on p.id = pe.participants_id and p.email != ?2 and p.status = 0", nativeQuery = true)
    List<Participant> getParticipantsWithoutThisEmail(Long id, String email);
}
