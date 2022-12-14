package com.example.calendar;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.calendar.controller.CalendarRestController;
import com.example.calendar.controller.EventRestController;
import com.example.calendar.controller.ParticipantRestController;
import com.example.calendar.controller.RestExceptionHandler;
import com.example.calendar.dto.EmailDto;
import com.example.calendar.dto.EventDto;
import com.example.calendar.dto.ParticipantFullDto;
import com.example.calendar.exception.ParticipantNotFoundException;
import com.example.calendar.model.Event;
import com.example.calendar.model.UserDetailsImpl;
import com.example.calendar.repository.EventRepository;
import com.example.calendar.repository.ParticipantRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;

import java.io.UnsupportedEncodingException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:test.properties")
class RoutineApplicationTest {
    @Autowired
    private CalendarRestController calendarRestController;
    @Autowired
    private EventRestController eventRestController;
    @Autowired
    private ParticipantRestController participantRestController;
    @Autowired
    private RestExceptionHandler restExceptionHandler;

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder encoder;
    @Autowired
    private ParticipantRepository participantRepository;
    @Autowired
    private EventRepository eventRepository;
    @Autowired
    private MockMvc mockMvc;
    @Test
    public void contextLoads() {
        assertThat(calendarRestController).isNotNull();
        assertThat(eventRestController).isNotNull();
        assertThat(participantRestController).isNotNull();
        assertThat(restExceptionHandler).isNotNull();
        assertThat(encoder).isNotNull();
        assertThat(objectMapper).isNotNull();
        assertThat(participantRepository).isNotNull();
        assertThat(eventRepository).isNotNull();
    }
    ParticipantFullDto createParticipantFullDto(String email) {
        return new ParticipantFullDto("name", "sername", "112101", email);
    }


    @BeforeEach
    public void addData() {
        var firstParticipant = createParticipantFullDto("first@gmail.com").toParticipant();
        firstParticipant.setPassword(encoder.encode(firstParticipant.getPassword()));
        var secondParticipant = createParticipantFullDto("second@gmail.com").toParticipant();
        var firstEvent = new EventDto(null, LocalDateTime.now().plusDays(1), LocalDateTime.now().plusDays(2), "descr").toEvent();
        secondParticipant.setPassword(encoder.encode(secondParticipant.getPassword()));
        var secondEvent = new EventDto(null, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4), "descr").toEvent();
        firstParticipant.addEvent(firstEvent);
        secondParticipant.addEvent(secondEvent);
        participantRepository.save(firstParticipant);
        participantRepository.save(secondParticipant);
    }
    @AfterEach
    public void resetDb() {
        participantRepository.deleteAll();
        eventRepository.deleteAll();
    }


    @Test
    void login () throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        //when
        var res = this.mockMvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("password", password)
                .param("email", email))
                .andReturn();
    }
    @Test
    void login_returnException () throws Exception {
        //given
        var email = "firs@gmail.com";
        var password = "112101";
        //then
        this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andExpect(jsonPath("errors", equalTo("bad password and/or email")));
    }

    @Test
    void regenerateAccessToken() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String refresh = JsonPath.parse(tokens.getResponse().getContentAsString()).read("refresh_token");
        //when
        var res = this.mockMvc.perform(get("/calendar/refresh")
                .header("Authorization", "Bearer " + refresh))
                .andReturn();
        //then
        assertNotNull( JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token"));
    }

    @Test
    void findAll() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        //then
        this.mockMvc.perform(get("/calendar")
                        .header("Authorization", "Bearer " + access))
                .andExpect(jsonPath("$", hasSize(2)));
    }
    @Test
    void addParticipant() throws Exception {
        //when
        var dto = createParticipantFullDto("changed@gmail.com");
        //then
        this.mockMvc.perform(post("/calendar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(jsonPath("firstName", equalTo("name")))
                .andExpect(jsonPath("lastName", equalTo("sername")));
    }

    @Test
    void getParticipant() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        //then
        this.mockMvc.perform(get("/calendar/user")
                        .header("Authorization", "Bearer " + access))
                .andExpect(jsonPath("email", equalTo(email)));
    }

    @Test
    void deleteParticipant() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        //then
        this.mockMvc.perform(delete("/calendar/user")
                        .header("Authorization", "Bearer " + access))
                .andExpect(status().isOk());
    }

    @Test
    void changeParticipant() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        //when
        var dto = createParticipantFullDto("changed@gmail.com");

        //then
        this.mockMvc.perform(patch("/calendar/user")
                        .header("Authorization", "Bearer " + access)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(jsonPath("email", equalTo(dto.getEmail())));
    }

    @Test
    public void getWithEvents() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        //then
        this.mockMvc.perform(get("/calendar/events")
                        .header("Authorization", "Bearer " + access))
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void addEvent() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        //when
        var dto = new EventDto(null, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4), "new description");
        //then
        this.mockMvc.perform(post("/calendar/events")
                        .header("Authorization", "Bearer " + access)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(jsonPath("description", equalTo(dto.getDescription())));
    }
    @Test
    public void addParticipantToEvent() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        var id = eventRepository.findAll().get(0).getId();
        var dto = new EmailDto("second@gmail.com");
        //then
        this.mockMvc.perform(post("/calendar/events/" + id)
                        .header("Authorization", "Bearer " + access)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk());


    }

    @Test
    public void changeEvent() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        var id = eventRepository.findAll().get(0).getId();
        var dto = new EventDto(id, LocalDateTime.now().plusDays(3), LocalDateTime.now().plusDays(4), "changed");
        this.mockMvc.perform(patch("/calendar/events")
                        .header("Authorization", "Bearer " + access)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(jsonPath("description", equalTo(dto.getDescription())));

    }

    @Test
    public void deleteEvent() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        var id = eventRepository.findAll().get(0).getId();
        //then
        this.mockMvc.perform(delete("/calendar/events/" + id)
                        .header("Authorization", "Bearer " + access))
                .andExpect(status().isOk());

    }

    @Test
    public void getParticipants() throws Exception {
        //given
        var email = "first@gmail.com";
        var password = "112101";
        var tokens = this.mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("password", password)
                        .param("email", email))
                .andReturn();
        String access = JsonPath.parse(tokens.getResponse().getContentAsString()).read("access_token");
        var id = eventRepository.findAll().get(0).getId();
        var res = this.mockMvc.perform(delete("/calendar/events/" + id)
                        .header("Authorization", "Bearer " + access))
                .andReturn();
        assertEquals(res.getResponse().getContentAsString(), "");
    }

}