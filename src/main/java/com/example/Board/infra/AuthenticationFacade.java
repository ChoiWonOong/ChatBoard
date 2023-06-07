package com.example.Board.infra;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthenticationFacade {
    public String getUsername(){
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return SecurityContextHolder.getContext().getAuthentication().getAuthorities();
    }
}
