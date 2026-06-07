package com.khaled_amin.book_social_network.security.provider;

import com.khaled_amin.book_social_network.identity.core.registry.ActorPrincipalResolverRegistry;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.AnonymousActor;
import com.khaled_amin.book_social_network.identity.core.model.SystemActor;
import com.khaled_amin.book_social_network.identity.core.provider.AuthenticatedActorProvider;
import com.khaled_amin.book_social_network.security.exception.SecurityTechnicalException;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class SpringSecurityAuthenticatedActorProvider implements AuthenticatedActorProvider {

    private final ActorPrincipalResolverRegistry actorPrincipalResolverRegistry;

    @Override
    public Actor getCurrentActor() {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        //  No authentication → system context (batch / startup operations / scheduled jobs / background processing / etc)
        if (auth == null) {
            return SystemActor.INSTANCE;
        }

        //  Anonymous request (not logged in) → anonymous context (e.g. public API [register, login, etc.])
        if (auth instanceof AnonymousAuthenticationToken) {
            return AnonymousActor.INSTANCE;
        }

        //  Not authenticated [Authentication exists but not valid] → fallback to anonymous context
        if (!auth.isAuthenticated()) {
            return AnonymousActor.INSTANCE;
        }

        Object principal = auth.getPrincipal();

        //  Valid authenticated principal → actor  (resolve via registry)
        if (principal instanceof AuthenticatedPrincipal authenticatedPrincipal) {
            return actorPrincipalResolverRegistry.resolve(authenticatedPrincipal);
        }


        // Unknown → fallback
        throw SecurityTechnicalException.unsupportedPrincipalType(principal.getClass())
                .withDebugDetails("principalType", principal.getClass().getName())
                .withDebugDetails("authenticationType", auth.getClass().getName())
                .withDebugDetails("authenticated", auth.isAuthenticated())
                .withDebugDetails("authenticationName", auth.getName());

    }

}
