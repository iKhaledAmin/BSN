package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;

public record EncodedPassword(String value) {

    public EncodedPassword {
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
                    .withDetail("reason", "Password must not be empty");
        }
    }

    public static EncodedPassword of(String encoded) {
        return new EncodedPassword(encoded);
    }
}