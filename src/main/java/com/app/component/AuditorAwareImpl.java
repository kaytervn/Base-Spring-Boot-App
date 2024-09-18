package com.app.component;

import io.micrometer.core.lang.NonNullApi;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NonNullApi
public class AuditorAwareImpl implements AuditorAware<String> {
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("anonymous_user");
        }
        return Optional.of(authentication.getName());
    }
}
