package com.example.routine.Repository;

import com.example.routine.Model.Participant;
import com.example.routine.Model.ParticipantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findParticipantByStatus(ParticipantStatus status);

    Optional<Participant> findByIdAndStatus(Long id, ParticipantStatus status);
}
