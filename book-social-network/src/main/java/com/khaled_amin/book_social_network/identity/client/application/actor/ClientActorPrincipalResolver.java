package com.khaled_amin.book_social_network.identity.client.application.actor;

import com.khaled_amin.book_social_network.identity.core.exception.IdentityTechnicalException;
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
            throw IdentityTechnicalException.principalTypeMismatch(
                    ClientPrincipal.class,
                    principal.getClass()
            );
        }

        // TODO: note that the scopes that come from the client principal in this format
        //  "SCOPE_<resource>:<action>"  example "SCOPE_books:read"
        //  but internally we use unique format as a capability
        //  "<resource>_<action>"  example "books_read"
        //  so later we need to convert the scopes to the internal format
        return new ClientActor(
                client.getActorCode(),
                client.getScopes()
        );
    }
}