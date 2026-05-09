package com.khaled_amin.book_social_network.identity.client.application.actor;

import com.khaled_amin.book_social_network.identity.core.model.AbstractActor;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

import java.util.Set;

public class ClientActor extends AbstractActor {

    private final Set<String> scopeNames;

    public ClientActor(Long clientId, Set<String> scopeNames) {
        super(
                ActorIdentity.of(ActorType.CLIENT, clientId.toString())
        );

        this.scopeNames = scopeNames;
    }

    @Override
    public boolean hasAuthority(String authority) {
        return scopeNames.contains(authority);
    }
}
