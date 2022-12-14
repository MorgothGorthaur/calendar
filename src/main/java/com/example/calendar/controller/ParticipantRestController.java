package com.example.calendar.controller;

import java.security.Principal;

import javax.validation.Valid;


import com.example.calendar.dto.ParticipantFullDto;
import com.example.calendar.model.ParticipantStatus;
import com.example.calendar.repository.ParticipantRepository;
import com.example.calendar.exception.EmailNotUnique;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/calendar/user")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class ParticipantRestController {

    private final ParticipantRepository participantRepository;
    private final PasswordEncoder encoder;

    private final ModelMapper modelMapper;

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ParticipantFullDto getParticipant(Principal principal){
        var participant = participantRepository.findByEmailAndStatus(principal.getName(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setPassword(null);
        return modelMapper.map(participant, ParticipantFullDto.class);
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping
    public void deleteParticipant(Principal principal) {
        var participant = participantRepository.findByEmailAndStatus(principal.getName(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setStatus(ParticipantStatus.REMOVED);
        participantRepository.save(participant);
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PatchMapping
    public ParticipantFullDto changeParticipant(Principal principal, @Valid @RequestBody ParticipantFullDto dto) {
        var participant = participantRepository.findByEmailAndStatus(principal.getName(), ParticipantStatus.ACTIVE)
                .orElseThrow(() -> new ParticipantNotFoundException(principal.getName()));
        participant.setFirstName(dto.getFirstName());
        participant.setLastName(dto.getLastName());
        participant.setEmail(dto.getEmail());
        participant.setPassword(encoder.encode(dto.getPassword()));
        if (participantRepository.findParticipantsWithEqualEmailAndNonEqualId(participant.getEmail(), participant.getId()).size() != 0) {
            throw new EmailNotUnique(participant.getEmail());
        }
        participantRepository.save(participant);
        return dto;
    }


}
