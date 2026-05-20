package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.TechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

public class PrincipalResolutionException extends TechnicalException {

    private PrincipalResolutionException(PrincipalResolutionError error) {
        super(error);
    }

    private PrincipalResolutionException(PrincipalResolutionError error, String message) {
        super(error, message);
    }

    private PrincipalResolutionException(PrincipalResolutionError error, String message, Throwable cause) {
        super(error, message, cause);
    }

    public static PrincipalResolutionException duplicateResolver(ActorType type) {

        return new PrincipalResolutionException(
                PrincipalResolutionError.PRINCIPAL_DUPLICATE_RESOLVER,
                "Duplicate PrincipalResolver registered for ActorType: " + type
        );
    }

    public static PrincipalResolutionException missingResolver(ActorType type) {

        return new PrincipalResolutionException(
                PrincipalResolutionError.PRINCIPAL_MISSING_RESOLVER,
                "No PrincipalResolver registered for ActorType: " + type
        );
    }
}