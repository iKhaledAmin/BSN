package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.domain.exception.EmailDomainException;

public record Subject(String value) {

    public Subject {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw EmailDomainException.invalidSubject()
                    .withDetail("reason", "Subject of email must not be empty");
        }

        if (value.length() > 255) {
            throw EmailDomainException.invalidSubject()
                    .withDetail("reason", "Subject too long");
        }
    }

    public static Subject of(String value) {
        return new Subject(value);
    }
}