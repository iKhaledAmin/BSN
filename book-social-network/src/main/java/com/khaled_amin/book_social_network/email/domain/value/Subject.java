package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.exception.EmailValidationException;

public record Subject(String value) {

    public static final int MAX_LENGTH = 255;

    public Subject {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw EmailValidationException.invalidSubject()
                    .withClientDetails("reason", "Email subject must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw EmailValidationException.invalidSubject()
                    .withClientDetails("reason", "Email subject exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }
    }

    public static Subject of(String value) {
        return new Subject(value);
    }

    @Override
    public String toString() {
        return value;
    }
}