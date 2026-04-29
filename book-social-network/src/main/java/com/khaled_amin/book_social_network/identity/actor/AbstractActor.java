package com.khaled_amin.book_social_network.core.actor;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class AbstractActor implements Actor {

    private final ActorIdentity identity;

    @Override
    public ActorIdentity getIdentity() {
        return identity;
    }

    @Override
    public ActorType getType() {
        return identity.type();
    }

    @Override
    public boolean sameAs(ActorIdentity otherIdentity) {
        return identity.sameAs(otherIdentity);
    }

    @Override
    public boolean hasAnyRole(String... roleNames) {
        for (String role : roleNames) {
            if (hasRole(role)) return true;
        }
        return false;
    }

}
