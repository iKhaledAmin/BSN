package com.khaled_amin.book_social_network.identity.client.application.actor;

import com.khaled_amin.book_social_network.identity.core.model.AbstractActor;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

import java.util.Set;

/**
 * Business actor representation of an authenticated client.
 *
 * <p>
 * Represents machine/application identities interacting
 * with the system.
 * </p>
 *
 * <p>
 * Authorities are resolved from granted scopes.
 * </p>
 */
public class ClientActor extends AbstractActor {

    private final Set<String> scopes;

    public ClientActor(ActorCode actorCode, Set<String> scopes) {
        super(
                ActorIdentity.of(ActorType.CLIENT, actorCode)
        );

        this.scopes = scopes;
    }

    @Override
    public boolean hasAuthority(String authority) {
        return scopes.contains(authority);
    }
}