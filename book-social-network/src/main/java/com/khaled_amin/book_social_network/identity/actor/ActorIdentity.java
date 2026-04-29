package com.khaled_amin.book_social_network.core.actor;

public record ActorIdentity(ActorType type, String id) {

    public boolean sameAs(ActorIdentity otherIdentity) {
        if (otherIdentity == null) return false;
        return this.type == otherIdentity.type && this.id.equals(otherIdentity.id);
    }

    public static ActorIdentity of(ActorType type, String id) {

        if (type == null )
            throw new IllegalArgumentException("Actor identity type must not be null");

        if (id == null )
            throw new IllegalArgumentException("Actor identity id must not be null");

        return new ActorIdentity(type, id);
    }
}