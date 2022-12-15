package com.example.calendar.service;

import com.example.calendar.model.UserDetailsImpl;
import com.example.calendar.repository.ParticipantRepository;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final ParticipantRepository participantRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(participantRepository.findByEmail(username).orElseThrow(() -> new ParticipantNotFoundException(username)));
    }
}
