package com.khaled_amin.book_social_network.audit;

import com.khaled_amin.book_social_network.user.model.entity.Account;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AuditAwareImpl implements AuditorAware<String> {

    private static final String SYSTEM = "System";
    private static final String ANONYMOUS = "Anonymous";

    @Override
    public Optional<String> getCurrentAuditor() {

        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        // Application startup / system process
        if (authentication == null) {
            return Optional.of(SYSTEM);
        }

        // Anonymous user
        if (authentication instanceof AnonymousAuthenticationToken) {
            return Optional.of(ANONYMOUS);
        }

        // Not authenticated
        if (!authentication.isAuthenticated()) {
            return Optional.of(ANONYMOUS);
        }

        Object principal = authentication.getPrincipal();

        // Authenticated user
        if (principal instanceof Account user) {
            return Optional.of(user.getId().toString());
        }

        return Optional.of(SYSTEM);
    }
}

