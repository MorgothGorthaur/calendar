package com.example.calendar.Controller;

import com.example.calendar.DTO.ParticipantDto;
import com.example.calendar.DTO.ParticipantFullDto;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.exception.EmailNotUnique;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/calendar")
@CrossOrigin(origins = "*")
@AllArgsConstructor
public class CalendarRestController {
    private ParticipantRepository participantRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder encoder;

    @GetMapping
    public List<ParticipantDto> findAll() {
        return participantRepository.findParticipantByStatus(ParticipantStatus.ACTIVE).stream().
                map(participant -> modelMapper.map(participant, ParticipantDto.class)).toList();
    }


    @PostMapping
    public ParticipantDto addParticipant(@Valid @RequestBody ParticipantFullDto dto) {
        var participant = dto.toParticipant();
        participant.setPassword(encoder.encode(participant.getPassword()));
        if (participantRepository.findParticipantsWithEqualEmailAndNonEqualId(participant.getEmail(), 0L).size() != 0) {
            throw new EmailNotUnique(participant.getEmail());
        }
        return modelMapper.map(participantRepository.save(participant), ParticipantDto.class);
    }

}
