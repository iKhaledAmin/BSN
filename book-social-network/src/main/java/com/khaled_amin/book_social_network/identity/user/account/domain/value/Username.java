package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;


public record Username(String value) {

    public Username {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw AccountDomainException
                    .invalidUsername()
                    .withDetail("reason", "Username must not be null or empty");
        }

        if (value.length() > 50) {
            throw AccountDomainException
                    .invalidUsername()
                    .withDetail("reason", "Username too long");
        }
    }

    public static Username of(String username) {
        return new Username(username);
    }
}