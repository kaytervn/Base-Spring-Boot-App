package com.app.service.impl;

import com.app.service.AuthenticationFacadeService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuthenticationFacadeServiceImpl implements AuthenticationFacadeService {
    @Override
    public Authentication getAuthentication() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .orElseThrow(() -> new SecurityException("Invalid token"));
    }
}
