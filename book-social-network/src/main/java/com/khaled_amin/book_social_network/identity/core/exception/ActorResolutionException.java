package com.khaled_amin.book_social_network.identity.core.exception;

import com.khaled_amin.book_social_network.core.exception.TechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

public class ActorResolutionException extends TechnicalException {

    protected ActorResolutionException(ActorResolutionError error) {
        super(error);
    }

    protected ActorResolutionException(ActorResolutionError error, Throwable cause) {
        super(error, cause);
    }

    protected ActorResolutionException(ActorResolutionError error, String message) {
        super(error, message);
    }

    protected ActorResolutionException(ActorResolutionError error, String message, Throwable cause) {
        super(error, message, cause);
    }

    public static ActorResolutionException sourceTypeMismatch(Class<?> expected, Class<?> actual) {

        return new ActorResolutionException(
                ActorResolutionError.ACTOR_SOURCE_TYPE_MISMATCH,
                "ActorSource type mismatch: expected "
                        + expected.getName()
                        + " but got "
                        + actual.getName()
        );
    }

    public static ActorResolutionException principalTypeMismatch(Class<?> expected, Class<?> actual) {

        return new ActorResolutionException(
                ActorResolutionError.ACTOR_PRINCIPAL_TYPE_MISMATCH,
                "Principal type mismatch: expected "
                        + expected.getName()
                        + " but got "
                        + actual.getName()
        );
    }

    public static ActorResolutionException resolverNotFound(ActorType type) {

        return new ActorResolutionException(
                ActorResolutionError.ACTOR_RESOLVER_NOT_FOUND,
                "No resolver registered for ActorType: " + type
        );
    }

    public static ActorResolutionException registryConflict(ActorType type) {

        return new ActorResolutionException(
                ActorResolutionError.ACTOR_REGISTRY_CONFLICT,
                "Duplicate resolver registration for ActorType: " + type
        );
    }

    public static ActorResolutionException unsupportedActorType() {

        return new ActorResolutionException(
                ActorResolutionError.ACTOR_UNSUPPORTED_TYPE
        );
    }

    public static ActorResolutionException invalidActorType(String value) {

        return new ActorResolutionException(
                ActorResolutionError.ACTOR_INVALID_TYPE,
                "Invalid actor type: " + value
        );
    }

    public static ActorResolutionException invalidActorType(String value, Exception ex) {

        return new ActorResolutionException(
                ActorResolutionError.ACTOR_INVALID_TYPE,
                "Invalid actor type: " + value,
                ex
        );
    }
}