package auth.base.user.component;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NonNullApi
public class AuditorAwareImpl implements AuditorAware<String> {
    
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.of("anonymous");
        }
        return Optional.of(authentication.getName());
    }
}
