package auth.base.user.service;

import org.springframework.security.core.Authentication;

@FunctionalInterface
public interface AuthenticationFacadeService {
    Authentication getAuthentication();
}
