package com.khaled_amin.book_social_network.identity.core.exception;

import com.khaled_amin.book_social_network.core.exception.SystemException;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

public class ActorResolutionException extends SystemException {


    protected ActorResolutionException(String code, String message) {
        super(code, message);
    }

    public static ActorResolutionException sourceTypeMismatch(Class<?> expected, Class<?> actual) {
        return new ActorResolutionException(
                "ACTOR_SOURCE_TYPE_MISMATCH",
                "ActorSource type mismatch: expected " + expected.getName() +
                        " but got " + actual.getName()
        );
    }

    public static ActorResolutionException principalTypeMismatch(Class<?> expected, Class<?> actual) {
        return new ActorResolutionException(
                "ACTOR_PRINCIPAL_TYPE_MISMATCH",
                "Principal type mismatch: expected " + expected.getName() +
                        " but got " + actual.getName()
        );
    }

    public static ActorResolutionException resolverNotFound(ActorType type) {
        return new ActorResolutionException(
                "ACTOR_RESOLVER_NOT_FOUND",
                "No resolver registered for ActorType: " + type
        );
    }

    public static ActorResolutionException registryConflict(ActorType type) {
        return new ActorResolutionException(
                "ACTOR_REGISTRY_CONFLICT",
                "Duplicate resolver registration for ActorType: " + type
        );
    }

    public static ActorResolutionException unsupportedActorType() {
        return new ActorResolutionException("ACTOR_UNSUPTED_TYPE", "Unsupported actor type");
    }
}