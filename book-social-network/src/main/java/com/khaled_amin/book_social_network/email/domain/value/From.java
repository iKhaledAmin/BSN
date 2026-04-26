package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.domain.exception.EmailDomainException;

public record From(String value) {

    public From {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw EmailDomainException.invalidEmailAddress()
                    .withDetail("reason", "From email address must not be null or empty");
        }

        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) { // regex for email validation
            // valid email example: "khaled_amin@example.com"
            throw EmailDomainException.invalidEmailAddress()
                    .withDetail("reason", "From email address not valid email format")
                    .withDetail("invalidEmail", value);
        }
    }

    public static From of(String value) {
        return new From(value);
    }
}