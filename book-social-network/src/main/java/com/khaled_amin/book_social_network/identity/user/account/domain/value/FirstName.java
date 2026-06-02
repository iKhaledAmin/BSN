package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.exception.AccountValidationException;

public record FirstName(String value) {

    public static final int MAX_LENGTH = 50;

    /**
     * Human-readable first name format.
     */
    public static final String PATTERN = "^[A-Za-z]+(?: [A-Za-z]+)*$";

    public FirstName {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim().replaceAll("\\s+", " ");
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw AccountValidationException.invalidFirstName()
                    .withClientDetails("reason", "First name must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw AccountValidationException.invalidFirstName()
                    .withClientDetails("reason", "First name exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw AccountValidationException.invalidFirstName()
                    .withClientDetails("reason", "First name must contain only letters and spaces")
                    .withClientDetails("expectedFormat", "letters_and_spaces_only")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
        }
    }

    public static FirstName of(String value) {
        return new FirstName(value);
    }

    @Override
    public String toString() {
        return value;
    }
}