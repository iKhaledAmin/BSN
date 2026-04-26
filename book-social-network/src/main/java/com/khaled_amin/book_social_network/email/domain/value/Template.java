package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.domain.exception.EmailDomainException;

public record Template(String value) {

    public Template {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {
        return value == null ? null : value.trim();
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw EmailDomainException.invalidTemplate()
                    .withDetail("reason", "Template of email must not be empty");
        }
    }

    public static Template of(String value) {
        return new Template(value);
    }
}