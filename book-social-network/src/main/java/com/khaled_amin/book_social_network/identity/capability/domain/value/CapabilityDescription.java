package com.khaled_amin.book_social_network.identity.capability.domain.value;

import com.khaled_amin.book_social_network.identity.capability.exception.CapabilityValidationException;

public record CapabilityDescription(String value) {

    public static final int MAX_LENGTH = 250;

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

        // optional field
        if (value == null) {
            return;
        }

        if (value.length() > MAX_LENGTH) {
            throw CapabilityValidationException.invalidDescription()
                    .withClientDetails("reason", "Capability description exceeds maximum allowed length")
                    .withClientDetails("maxLength", MAX_LENGTH)
                    .withDebugDetails("actualLength", value.length())
                    .withDebugDetails("receivedValue", value);
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