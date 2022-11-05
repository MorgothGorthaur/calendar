package com.example.calendar.Controller;


import com.example.calendar.DTO.EventDto;
import com.example.calendar.DTO.ParticipantDto;
import com.example.calendar.Model.Event;
import com.example.calendar.Model.Participant;
import com.example.calendar.Model.ParticipantStatus;
import com.example.calendar.Repository.EventRepository;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.Service.AuthorService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoutineRestController.class)
@AutoConfigureMockMvc
@ExtendWith({SpringExtension.class})
class RoutineRestControllerTest {
    @MockBean
    private EventRepository eventRepository;
    @MockBean
    private ParticipantRepository participantRepository;
    @MockBean
    private ModelMapper modelMapper;
    @MockBean
    private AuthorService authorService;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    private MockMvc mock;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() throws Exception {
        //given
        var participant = new Participant();
        participant.setId(1L);
        participant.setFirstName("Victor");
        participant.setLastName("Tarasov");
        participant.setStatus(ParticipantStatus.ACTIVE);
        var dto = new ParticipantDto(1L, "Victor", "Tarasov");
        when(participantRepository.findParticipantByStatus(ParticipantStatus.ACTIVE)).thenReturn(List.of(participant));
        when(modelMapper.map(participant, ParticipantDto.class)).thenReturn(dto);
        //when
        this.mock.perform(get("/routine")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName",equalTo("Victor")))
                .andExpect(jsonPath("$[0].lastName", equalTo("Tarasov")));
    }

    @Test
    void addParticipant() throws Exception {
        //given
        var participant = new Participant();
        participant.setId(1L);
        participant.setFirstName("Victor");
        participant.setLastName("Tarasov");
        participant.setStatus(ParticipantStatus.ACTIVE);
        var dto = new ParticipantDto(1L, "Victor", "Tarasov");
        when(participantRepository.save(participant)).thenReturn(participant);
        when(modelMapper.map(participant, ParticipantDto.class)).thenReturn(dto);
        //when
        this.mock.perform(post("/routine")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("firstName",equalTo("Victor")))
                .andExpect(jsonPath("lastName", equalTo("Tarasov")))
                .andExpect(jsonPath("id",equalTo(1)));
    }

    @Test
    void getWithEvents() throws Exception {
        //when
        var participant = new Participant();
        var now = LocalDateTime.now();
        var event = new Event();
        event.setId(1L);
        event.setDescription("descr");
        event.setStartTime(now.plusDays(1));
        event.setEndTime(now.plusDays(2));
        participant.setEvents(List.of(event));
        var dto = new EventDto(event.getId(), event.getStartTime(), event.getEndTime(), event.getDescription());
        when(participantRepository.findByIdAndStatus(1L, ParticipantStatus.ACTIVE)).thenReturn(Optional.of(participant));
        when(participantRepository.save(participant)).thenReturn(participant);
        when(modelMapper.map(event, EventDto.class)).thenReturn(dto);
        //when
        this.mock.perform(get("/routine/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", equalTo(1)))
                .andExpect(jsonPath("$[0].description", equalTo("descr")))
                .andExpect(jsonPath("$[0].startTime", equalTo(String.valueOf(now.plusDays(1)))))
                .andExpect(jsonPath("$[0].endTime", equalTo(String.valueOf(now.plusDays(2)))));

    }

    @Test
    void deleteParticipant() throws Exception {
        //given
        var participant = new Participant();
        when(participantRepository.findByIdAndStatus(1L, ParticipantStatus.ACTIVE)).thenReturn(Optional.of(participant));
        when(participantRepository.save(participant)).thenReturn(participant);
        //when
        MvcResult res = this.mock.perform(delete("/routine/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andReturn();
        assertEquals(res.getResponse().getContentAsString(),"deleted");
    }

    @Test
    void changeParticipant() throws Exception {
        var dto = new ParticipantDto(1L, "Victor", "Tarasov");
        var participant = new Participant();
        when(participantRepository.findByIdAndStatus(dto.getId(), ParticipantStatus.ACTIVE)).thenReturn(Optional.of(participant));
        when(participantRepository.save(participant)).thenReturn(participant);
        //when
        this.mock.perform(patch("/routine")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("firstName",equalTo("Victor")))
                .andExpect(jsonPath("lastName", equalTo("Tarasov")))
                .andExpect(jsonPath("id",equalTo(1)));
    }

    @Test
    void addEvent() throws Exception {
        var participant = new Participant();
        var now = LocalDateTime.now();
        var event = new Event();
        event.setDescription("descr");
        event.setStartTime(now.plusDays(1));
        event.setEndTime(now.plusDays(2));
        var dto = new EventDto(event.getId(), event.getStartTime(), event.getEndTime(), event.getDescription());
        when(authorService.addEvent(participant, event)).thenReturn(List.of(event));
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        when(modelMapper.map(event, EventDto.class)).thenReturn(dto);
        this.mock.perform(post("/routine/1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("description", equalTo("descr")))
                .andExpect(jsonPath("startTime", equalTo(String.valueOf(now.plusDays(1)))))
                .andExpect(jsonPath("endTime", equalTo(String.valueOf(now.plusDays(2)))));

    }

    @Test
    void changeEvent() throws Exception {
        var now = LocalDateTime.now();
        var event = new Event();
        event.setId(1L);
        event.setDescription("descr");
        event.setStartTime(now.plusDays(1));
        event.setEndTime(now.plusDays(2));
        var participant = new Participant();
        participant.setEvents(List.of(event));
        var dto = new EventDto(event.getId(), event.getStartTime(), event.getEndTime(), event.getDescription());
        when(participantRepository.findByIdAndStatus(1L, ParticipantStatus.ACTIVE)).thenReturn(Optional.of(participant));
        when(authorService.addEvent(participant, dto.toEvent())).thenReturn(List.of(event));
        when(modelMapper.map(event, EventDto.class)).thenReturn(dto);
        this.mock.perform(patch("/routine/1/events")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id", equalTo(1)))
                .andExpect(jsonPath("description", equalTo("descr")))
                .andExpect(jsonPath("startTime", equalTo(String.valueOf(now.plusDays(1)))))
                .andExpect(jsonPath("endTime", equalTo(String.valueOf(now.plusDays(2)))));
    }

    @Test
    void deleteEvent() throws Exception {
        var participant = new Participant();
        var event = new Event();
        when(participantRepository.findById(1L)).thenReturn(Optional.of(participant));
        when(eventRepository.findById(1L)).thenReturn(Optional.of(event));
        when(participantRepository.save(participant)).thenReturn(participant);
        MvcResult res = this.mock.perform(delete("/routine/1/events/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andReturn();
        assertEquals(res.getResponse().getContentAsString(),"deleted");
    }
}