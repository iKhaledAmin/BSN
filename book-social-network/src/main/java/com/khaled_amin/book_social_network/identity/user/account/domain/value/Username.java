package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.exception.AccountValidationException;

public record Username(String value) {

    public static final int MAX_LENGTH = 100;

    /**
     * Canonical username format.
     */
    public static final String PATTERN = "^[a-zA-Z0-9._]+$";

    public Username {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw AccountValidationException.invalidUsername()
                    .withClientDetails("reason", "Username must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw AccountValidationException.invalidUsername()
                    .withClientDetails("reason", "Username exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw AccountValidationException.invalidUsername()
                    .withClientDetails("reason", "Username format is invalid")
                    .withClientDetails("expectedFormat", "letters_numbers_dots_underscores")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
        }
    }

    public static Username of(String value) {
        return new Username(value);
    }

    @Override
    public String toString() {
        return value;
    }
}