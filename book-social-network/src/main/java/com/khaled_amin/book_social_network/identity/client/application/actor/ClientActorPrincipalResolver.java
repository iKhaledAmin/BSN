package com.khaled_amin.book_social_network.identity.client.application.actor;

import com.khaled_amin.book_social_network.identity.core.exception.ActorResolutionException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorPrincipalResolver;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.clinet.ClientPrincipal;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

@Component
public class ClientActorPrincipalResolver implements ActorPrincipalResolver {

    @Override
    public ActorType getType() {
        return ActorType.CLIENT;
    }

    @Override
    public Actor resolve(AuthenticatedPrincipal principal) {

        if (!(principal instanceof ClientPrincipal client)) {
            throw ActorResolutionException.principalTypeMismatch(
                    ClientPrincipal.class,
                    principal.getClass()
            );
        }

        return new ClientActor(
                client.getActorCode(),
                client.getScopes()
        );
    }
}