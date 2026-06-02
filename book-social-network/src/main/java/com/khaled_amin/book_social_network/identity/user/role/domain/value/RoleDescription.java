package com.khaled_amin.book_social_network.identity.user.role.domain.value;

import com.khaled_amin.book_social_network.identity.user.role.exception.RoleValidationException;

public record RoleDescription(String value) {

    public static final int MAX_LENGTH = 255;

    public RoleDescription {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {

        if (value == null) {
            return null;
        }

        value = value.trim();

        return value.isBlank() ? null : value;
    }

    private static void validate(String value) {

        // optional field
        if (value == null) {
            return;
        }

        if (value.length() > MAX_LENGTH) {
            throw RoleValidationException.invalidDescription()
                    .withClientDetails("reason", "Role description exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }
    }

    public static RoleDescription of(String value) {
        return new RoleDescription(value);
    }

    @Override
    public String toString() {
        return value;
    }
}