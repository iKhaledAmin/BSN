package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;


public record FirstName(String value) {

    public FirstName {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw AccountDomainException
                    .invalidFirstName()
                    .withDetail("reason", "First value must not be empty");
        }

        if (!value.matches("^[a-zA-Z ]+$")) {
            throw AccountDomainException
                    .invalidFirstName()
                    .withDetail("reason", "Only letters allowed");
        }

        if (value.length() > 50) {
            throw AccountDomainException
                    .invalidFirstName()
                    .withDetail("reason", "Too long");
        }
    }

    public static FirstName of(String firstName) {
        return new FirstName(firstName);
    }
}