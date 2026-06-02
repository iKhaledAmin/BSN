package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.exception.EmailValidationException;

public record EmailAddress(String value) {

    public static final int MAX_LENGTH = 100;

    /**
     * Standard email address format.
     */
    public static final String PATTERN =
            "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";

    public EmailAddress {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim().toLowerCase();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw EmailValidationException.invalidEmailAddress()
                    .withClientDetails("reason", "Email address must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw EmailValidationException.invalidEmailAddress()
                    .withClientDetails("reason", "Email address exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }

        if (!value.matches(PATTERN)) {
            throw EmailValidationException.invalidEmailAddress()
                    .withClientDetails("reason", "Email address format is invalid")
                    .withClientDetails("expectedFormat", "example@domain.com")
                    .withDebugDetails("receivedValue", value)
                    .withDebugDetails("pattern", PATTERN);
        }
    }

    public static EmailAddress of(String value) {
        return new EmailAddress(value);
    }

    @Override
    public String toString() {
        return value;
    }
}