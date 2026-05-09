package com.khaled_amin.book_social_network.security.provider;

import com.khaled_amin.book_social_network.identity.core.exception.ActorResolutionException;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorPrincipalResolverRegistry;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.AnonymousActor;
import com.khaled_amin.book_social_network.identity.core.model.SystemActor;
import com.khaled_amin.book_social_network.identity.core.provider.AuthenticatedActorProvider;
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
            return new SystemActor();
        }

        //  Anonymous request (not logged in) → anonymous context (e.g. public API [register, login, etc.])
        if (auth instanceof AnonymousAuthenticationToken) {
            return new AnonymousActor();
        }

        //  Not authenticated [Authentication exists but not valid] → fallback to anonymous context
        if (!auth.isAuthenticated()) {
            return new AnonymousActor();
        }

        Object principal = auth.getPrincipal();

        //  Valid authenticated principal → actor  (resolve via registry)
        if (principal instanceof AuthenticatedPrincipal authenticatedPrincipal) {
            return actorPrincipalResolverRegistry.resolve(authenticatedPrincipal);
        }


        // Unknown → fallback
        throw ActorResolutionException.unsupportedActorType();
    }


}
