package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;

public record LastName(String value) {

    public LastName {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw AccountDomainException
                    .invalidLastName()
                    .withDetail("reason", "Last value must not be empty");
        }

        if (!value.matches("^[a-zA-Z ]+$")) {
            throw AccountDomainException
                    .invalidLastName()
                    .withDetail("reason", "Only letters allowed");
        }

        if (value.length() > 50) {
            throw AccountDomainException
                    .invalidLastName()
                    .withDetail("reason", "Too long");
        }
    }

    public static LastName of(String lastName) {
        return new LastName(lastName);
    }
}