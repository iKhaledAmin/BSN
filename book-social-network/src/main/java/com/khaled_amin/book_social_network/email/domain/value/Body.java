package com.khaled_amin.book_social_network.email.domain.value;

import com.khaled_amin.book_social_network.email.domain.exception.EmailDomainException;

public record Body(String value) {

    public Body {
        validate(value);
    }

    private static void validate(String value) {
        if (value == null || value.isBlank()) {
            throw EmailDomainException.invalidBody()
                    .withDetail("reason", "Body of email must not be empty");
        }
    }

    public static Body of(String value) {
        return new Body(value);
    }
}