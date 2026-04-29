package com.khaled_amin.book_social_network.user.domain.value;

import com.khaled_amin.book_social_network.user.domain.exception.AccountDomainException;

public record PhoneNumber(String value) {

    public PhoneNumber {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw AccountDomainException
                    .invalidPhoneNumber()
                    .withDetail("reason", "Phone number must not be empty");
        }

        if (!value.matches("^\\+?[0-9]{10,15}$")) {
            throw AccountDomainException
                    .invalidPhoneNumber()
                    .withDetail("reason", "Invalid phone number format");
        }
    }

    public static PhoneNumber of(String value) {
        return new PhoneNumber(value);
    }
}