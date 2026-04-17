package com.khaled_amin.book_social_network.core.actor;


public class AnonymousActor extends AbstractActor {

    public AnonymousActor() {
        super(ActorIdentity.of(ActorType.ANONYMOUS, "ANONYMOUS"));
    }

    @Override
    public boolean hasRole(String role) {
        return false;
    }

}