package com.khaled_amin.book_social_network.identity.user.account.domain.value;

import com.khaled_amin.book_social_network.identity.user.account.domain.exception.AccountDomainException;

public record Profession(String value) {

    public Profession {
        if (value == null || value.isBlank()) {
            throw AccountDomainException
                    .invalidProfession();
        }

        value = value.trim();

        if (value.length() > 50) {
            throw AccountDomainException.invalidProfession()
                    .withDetail("reason", "Too long");
        }

    }

    public static Profession of(String value) {
        return new Profession(value);
    }
}