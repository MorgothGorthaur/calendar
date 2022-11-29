package com.example.calendar.Service;

import com.example.calendar.Model.UserDetailsImpl;
import com.example.calendar.Repository.ParticipantRepository;
import com.example.calendar.exception.ParticipantNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class JpaUerDetailsService implements UserDetailsService {
    private ParticipantRepository participantRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return new UserDetailsImpl(participantRepository.findByEmail(username).orElseThrow(() -> new ParticipantNotFoundException(username)));
    }
}
