package com.khaled_amin.book_social_network.user.domain.value;

import com.khaled_amin.book_social_network.user.domain.exception.AccountDomainException;

public record Email(String value) {

    public Email {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw AccountDomainException
                    .invalidEmail()
                    .withDetail("reason", "Email must not be null or empty");
        }

        if (!value.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            throw AccountDomainException
                    .invalidEmail()
                    .withDetail("reason", "Invalid email format");
        }
    }

    public static Email of(String email) {
        return new Email(email);
    }
}