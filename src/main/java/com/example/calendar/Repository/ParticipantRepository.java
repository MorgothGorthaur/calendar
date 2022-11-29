package com.example.calendar.Repository;

import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findParticipantByStatus(ParticipantStatus status);
    Optional<Participant> findByEmail(String email);
    @Query(value = "select p from Participant p where p.email = ?1 and p.id <> ?2")
    List<Participant> findParticipantsWithEqualEmailAndNonEqualId(String email, Long id);
}
