package com.app.service;

import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface AuthenticationFacadeService {
    Authentication getAuthentication();
}
