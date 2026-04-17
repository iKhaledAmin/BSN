package com.khaled_amin.book_social_network.core.actor;


public class SystemActor extends AbstractActor {

    public SystemActor() {
        super(ActorIdentity.of(ActorType.SYSTEM, "SYSTEM"));
    }

    @Override
    public boolean hasRole(String role) {
        return false;
    }

}