package com.example.routine.Repository;

import com.example.routine.Model.Participant;
import com.example.routine.Model.ParticipantStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ParticipantRepository extends JpaRepository<Participant, Long> {
    List<Participant> findParticipantByStatus(ParticipantStatus.ACTIVE);
}
