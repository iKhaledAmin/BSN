package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.domain.exception.CapabilityDomainException;

public record CapabilityDescription(String value) {

    public static final int MAX_LENGTH = 500;

    public CapabilityDescription {
        value = normalize(value);
        validate(value);
    }

    private static String normalize(String value) {

        if (value == null) {
            return null;
        }

        value = value.trim();

        return value.isBlank() ? null : value;
    }

    private static void validate(String value) {

        // Optional field
        if (value == null || value.isEmpty()) {
            return;
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityDomainException
                    .invalidCommand()
                    .withDetail("reason", "Capability description too long")
                    .withDetail("maxLength", MAX_LENGTH);
        }
    }

    public static CapabilityDescription of(String value) {
        return new CapabilityDescription(value);
    }

    @Override
    public String toString() {
        return value;
    }
}