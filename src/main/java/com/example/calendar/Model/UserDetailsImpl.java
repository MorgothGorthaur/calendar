package com.example.calendar.Model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

public class UserDetailsImpl implements UserDetails {
    private Participant participant;
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Set.of(participant.getRole());
    }

    @Override
    public String getPassword() {
        return participant.getPassword();
    }

    @Override
    public String getUsername() {
        return participant.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return participant.getStatus().equals(ParticipantStatus.ACTIVE);
    }
}
