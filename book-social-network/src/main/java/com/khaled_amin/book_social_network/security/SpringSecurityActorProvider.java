package com.khaled_amin.book_social_network.security;

import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.core.actor.ActorProvider;
import com.khaled_amin.book_social_network.core.actor.AnonymousActor;
import com.khaled_amin.book_social_network.core.actor.SystemActor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SpringSecurityActorProvider implements ActorProvider {

    @Override
    public Actor getCurrentActor() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        // 1. No authentication → system context (batch / startup / scheduled jobs / background processing / etc)
        if (auth == null) {
            return new SystemActor();
        }

        // 2. Anonymous request (not logged in) → anonymous context (e.g. public API [register, login, etc.])
        if (auth instanceof AnonymousAuthenticationToken) {
            return new AnonymousActor();
        }

        // 3. Not authenticated [Authentication exists but not valid] → fallback to anonymous context
        if (!auth.isAuthenticated()) {
            return new AnonymousActor();
        }

        Object principal = auth.getPrincipal();

        // 4. Authenticated user
        if (principal instanceof SecurityUser securityUser) {
            return securityUser.toActor();
        }

        // 5. Unknown → fallback
        return new SystemActor();
    }


}
