package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.exception.AccountValidationException;

public record Profession(String value) {

    public static final int MAX_LENGTH = 100;

    public Profession {
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
            throw AccountValidationException.invalidProfession()
                    .withClientDetails("reason", "Profession exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }
    }

    public static Profession of(String value) {
        return new Profession(value);
    }

    @Override
    public String toString() {
        return value;
    }
}