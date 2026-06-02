package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.exception.EmailValidationException;

public record Template(String value) {

    public static final int MAX_LENGTH = 100;

    public Template {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw EmailValidationException.invalidTemplate()
                    .withClientDetails("reason", "Email template must not be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw EmailValidationException.invalidTemplate()
                    .withClientDetails("reason", "Email template exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
        }
    }

    public static Template of(String value) {
        return new Template(value);
    }

    @Override
    public String toString() {
        return value;
    }
}