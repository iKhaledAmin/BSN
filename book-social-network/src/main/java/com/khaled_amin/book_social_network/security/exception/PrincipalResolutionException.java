package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.SystemException;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

public class PrincipalResolutionException extends SystemException {

    private PrincipalResolutionException(String code, String message) {
        super(code, message);
    }

    public static PrincipalResolutionException duplicateResolver(ActorType type) {
        return new PrincipalResolutionException(
                "PRINCIPAL_DUPLICATE_RESOLVER",
                "Duplicate PrincipalResolver registered for ActorType: " + type
        );
    }

    public static PrincipalResolutionException missingResolver(ActorType type) {
        return new PrincipalResolutionException(
                "PRINCIPAL_MISSING_RESOLVER",
                "No PrincipalResolver registered for ActorType: " + type
        );
    }
}