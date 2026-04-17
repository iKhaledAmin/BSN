package com.khaled_amin.book_social_network.core.actor;


public class ServiceActor extends AbstractActor {

    public ServiceActor(String serviceName) {
        super(ActorIdentity.of(ActorType.SERVICE, serviceName));
    }

    @Override
    public boolean hasRole(String role) {
        return false; // or service-specific logic
    }
}