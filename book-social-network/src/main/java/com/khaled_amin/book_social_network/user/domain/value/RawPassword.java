package com.khaled_amin.book_social_network.user.domain.value;

import com.khaled_amin.book_social_network.user.domain.exception.AccountDomainException;

public record RawPassword(String value) {

    public RawPassword {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {

        if (value == null || value.isBlank()) {
            throw AccountDomainException
                    .invalidPassword()
                    .withDetail("reason", "Password must not be null or empty");
        }

        if (value.length() < 8) {
            throw AccountDomainException
                    .invalidPassword()
                    .withDetail("reason", "Password too short");
        }

        // if (!value.matches(".*[A-Z].*")) ...
        // if (!value.matches(".*[0-9].*")) ...
    }

    public static RawPassword of(String value) {
        return new RawPassword(value);
    }
}