package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.domain.exception.EmailDomainException;

public record To(String value) {

    public To {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw EmailDomainException.invalidEmailAddress()
                    .withDetail("reason", "To email address must not be null or empty");
        }

        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) { // regex for email validation
            // valid email example: "khaled_amin@example.com"
            throw EmailDomainException.invalidEmailAddress()
                    .withDetail("reason", "To email address not valid email format")
                    .withDetail("invalidEmail", value);
        }
    }

    public static To of(String value) {
        return new To(value);
    }
}