package com.khaled_amin.book_social_network.identity.core.provider;

import com.khaled_amin.book_social_network.identity.core.resolver.ActorSourceResolverRegistry;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorPrincipalResolverRegistry;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ActorProviderImpl implements ActorProvider {
    private final AuthenticatedActorProvider authenticatedActorProvider;
    private final ActorPrincipalResolverRegistry principalResolverRegistry;
    private final ActorSourceResolverRegistry sourceResolverRegistry;

    @Override
    public Actor getCurrent() {
        return authenticatedActorProvider.getCurrentActor();
    }

    @Override
    public Actor getFrom(ActorSource source) {
        return sourceResolverRegistry.resolve(source);
    }

    @Override
    public Actor getFrom(AuthenticatedPrincipal principal) {
        return principalResolverRegistry.resolve(principal);
    }


}
